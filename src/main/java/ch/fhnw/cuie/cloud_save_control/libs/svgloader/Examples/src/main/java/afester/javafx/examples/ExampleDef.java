/*
 * Copyright 2016 Andreas Fester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.fhnw.cuie.cloud_save_control.libs.svgloader.Examples.src.main.java.afester.javafx.examples;

import javafx.scene.image.Image;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;


public class ExampleDef {

    private String className;
    private String description;
    private Image screenShot;

    /**
     * Creates a new example definition.
     *
     * @param className  The name of the class which implements the example.
     * @param description  The description of the example.
     */
    public ExampleDef(String className, String description) {
        this.className = className;
        this.description = description;
    }

    
    /**
     * @return The fully qualified class name of the example which is defined by 
     *         this example definition.
     */
    public String getClassName() {
        return className;
    }


    /**
     * @return The description of the example which is defined by this example definition.
     */
    public String getDescription() {
        return description;
    }


    /**
     * @return An Image with a screenshot of this example. 
     */
    public Image getScreenShot() {
        if (screenShot == null) {
            String imageName = className.replace(".",  "/") + ".png";
            URL imageUrl = getClass().getClassLoader().getResource(imageName);
            if (imageUrl != null) {
                screenShot = new Image(imageUrl.toString());    
            }
        }

        return screenShot;
    }


    /**
     * Launches the example which is defined by this example definition.
     */
    public void run() {
        try {
            Class<?> clazz = Class.forName(className);

            // Runnable example = (Runnable) clazz.newInstance();
            // example.run();

            Object example = clazz.newInstance();
            Method runMethod = clazz.getMethod("run");
            runMethod.invoke(example);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("ExampleDef[class=%s, desc=%s]", className, description);
    }


}
