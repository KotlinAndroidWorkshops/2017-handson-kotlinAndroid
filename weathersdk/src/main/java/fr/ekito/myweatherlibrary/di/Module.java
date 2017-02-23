package fr.ekito.myweatherlibrary.di;

import android.app.Application;

/**
 * Inject Module
 * Help define instances
 */
public abstract class Module {

    /**
     * load module definition
     */
    public abstract void load();


    public Application getApplication(){
        return Inject.getApplicationContext();
    }

    /**
     * modules to load
     * @param modules
     */
    public void extend(Class<? extends Module>... modules) {
        for (Class<? extends Module> m : modules) {
            Inject.load(m);
        }
    }

    /**
     * provides several object instances
     * @param instances
     */
    public void provides(Object... instances) {
        for (Object o : instances) {
            Inject.add(o);
        }
    }

    /**
     * provide one object instance
     * @param instance
     * @param clazz
     */
    public void provide(Object instance, Class clazz){
        Inject.add(instance,clazz);
    }
}
