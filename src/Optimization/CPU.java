package Optimization;

import Extras.ExtraArray;
import Extras.ExtraFile;
import Extras.ExtraSystem;

import javax.management.InvalidAttributeValueException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class CPU extends Thread {
    class OptimFunction<T,R> implements Runnable, Function<T,R> {
        public Function<T,R> func;
        public T input;
        public R output;

        public OptimFunction(Function<T,R> func){
            this.func = func;
            this.input = null;
        }
        public OptimFunction(Function<T,R> func, T input){
            this.func = func;
            this.input = input;
        }

        @Override
        public R apply(T t) {
            return func.apply(t);
        }

        @Override
        public void run() {
            output = apply(input);
        }
    }
    class OptimMethod extends Thread {
        final public Method method;
        public Object[] inputs;
        public Object output;

        public OptimMethod(Method method){
            this.method = method;
            this.inputs = new Object[method.getParameterCount()];
        }

        public OptimMethod(Method method, Object... params) throws Exception {
            this.method = method;
            this.inputs = new Object[method.getParameterCount()];
            for (int i=0;i<params.length;i++){
                addInput(i,params[i]);
            }
        }

        public void addInput(int i, Object o) throws Exception {
            if (i >= this.inputs.length){
                throw new IndexOutOfBoundsException();
            }
            if (!o.getClass().equals(method.getParameterTypes()[i])){
                throw new InvalidAttributeValueException("Incorrect input type at index "+i+". "+method.getParameterTypes()[i]+" expected");
            }
            this.inputs[i] = o;
        }

        @Override
        public void run() {
            try {
                this.output = this.method.invoke(inputs);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Runnable[] runnables;
    private OptimFunction[] functions;
    private OptimMethod[] methods;
    private float cpuPct = 0.5f;

    public CPU(){
        this.runnables = new Runnable[0];
        this.functions = new OptimFunction[0];
        this.methods = new OptimMethod[0];
    }
    public CPU(Runnable... runnables) {
        this.runnables = runnables;
        this.functions = new OptimFunction[0];
        this.methods = new OptimMethod[0];
    }
    public CPU(Function... funcs){
        this.functions = new OptimFunction[funcs.length];
        for (int i=0;i<this.functions.length;i++){
            this.functions[i] = new OptimFunction(funcs[i]);
        }
        this.runnables = new Runnable[0];
        this.methods = new OptimMethod[0];
    }
    public CPU(Method... methods){
        this.methods = new OptimMethod[methods.length];
        for (int i=0;i<this.methods.length;i++){
            this.methods[i] = new OptimMethod(methods[i]);
        }
        this.runnables = new Runnable[0];
        this.functions = new OptimFunction[0];
    }

    public Object[] functionOutputs(){
        Object[] r = new Object[this.functions.length];
        for (int i=0;i<r.length;i++){
            r[i] = this.functions[i].output;
        }
        return r;
    }

    public Object[] methodOutputs(){
        Object[] r = new Object[this.methods.length];
        for (int i=0;i<r.length;i++){
            r[i] = this.methods[i].output;
        }
        return r;
    }

    public Object[] outputs(){
        return ExtraArray.concat(new Object[0], functionOutputs(),methodOutputs());
    }

    public int outputLength(){
        return functions.length+methods.length;
    }

    public void addRunnable(Runnable runnable){
        Runnable[] n = new Runnable[this.runnables.length+1];
        for (int i=0;i<this.runnables.length;i++){
            n[i] = this.runnables[i];
        }
        n[n.length-1] = runnable;
        this.runnables = n;
    }

    // DOESN'T WORK
    public void addRunnables(Runnable... runnables){
        this.runnables = ExtraArray.concat(new Runnable[0], this.runnables, runnables);
    }

    public void addFunctions(Function... funcs){
        OptimFunction[] functions = new OptimFunction[funcs.length];
        for (int i=0;i<funcs.length;i++){
            functions[i] = new OptimFunction(funcs[i]);
        }
        this.functions = ExtraArray.concat(this.functions,functions);
    }

    public <T> void setFunctionInput (int index, T input){
        this.functions[index].input = input;
    }

    public void addMethod(Method method, Object... params) throws Exception {
        this.methods = ExtraArray.concat(this.methods,new OptimMethod(method,params));
    }
    public void addMethods(Method... methods){
        OptimMethod[] functions = new OptimMethod[methods.length];
        for (int i=0;i<methods.length;i++){
            functions[i] = new OptimMethod(methods[i]);
        }
        this.methods = ExtraArray.concat(this.methods,functions);
    }


    public void setCPUPct(float pct){
        this.cpuPct = (pct < 0 ? 0 : (pct > 1 ? 1 : pct));
    }
    public float getCpuPct(){
        return this.cpuPct;
    }

    @Override
    public void run() {
        int smt = Math.round(ExtraSystem.env.availableThreads*cpuPct);
        if (smt <= 0){
            smt = 1;
        } else if (smt > ExtraSystem.env.availableThreads){
            smt = ExtraSystem.env.availableThreads;
        }
        Runnable[] runs = new Runnable[this.runnables.length+this.functions.length+this.methods.length];
        for (int i=0;i<this.runnables.length;i++){
            runs[i] = this.runnables[i];
        }
        for (int i=0;i<this.functions.length;i++){
            runs[i+this.runnables.length] = this.functions[i];
        }
        for (int i=0;i<this.methods.length;i++){
            runs[i+this.runnables.length+this.methods.length] = this.methods[i];
        }

        if (smt > runs.length){
            smt = runs.length;
        }
        Thread[] threads = new Thread[smt];
        int i = 0;
        while (i < smt){
            threads[i] = new Thread(runs[i]);
            threads[i].start();
            i++;
        }

        while (i < runs.length && !this.isInterrupted()){
            for (int j=0;j<threads.length;j++){
                if (threads[j].getState() == Thread.State.TERMINATED){
                    i++;
                    if (i >= runs.length){
                        break;
                    }
                    threads[j] = new Thread(runs[i]);
                    threads[j].start();
                }
            }
        }
    }
}
