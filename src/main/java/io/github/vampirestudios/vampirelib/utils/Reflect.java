/*
 * MIT License
 *
 * Copyright (c) 2019 Vampire Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.vampirestudios.vampirelib.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Reflect {
    public static Class getInnerClass(Class clazz, String name) {
        Class[] declaredClasses = clazz.getDeclaredClasses();
        for (Class c : declaredClasses) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public static Object constructClass(Class clazz, Object... args) {
        Constructor<?>[] cap = clazz.getDeclaredConstructors();
        for (Constructor<?> c : cap) {
            Class<?>[] types = c.getParameterTypes();
            boolean match = true;
            for (int t = 0; t < types.length; t++) {
                Class<?> c_type = types[t];
                if (args[t] == null)
                    continue;
                if (isInstanceOf(c_type, args[t].getClass()))
                    continue;
                match = false;
                break;
            }
            if (match) {
                c.setAccessible(true);
                try {
                    return c.newInstance(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
	/*public static Object getMemberByType(Class field_class, Class object_class)
	{
		return getMemberByType(field_class, object_class, null);
	}*/

    public static Field getFieldByType(Class object_class, Class field_class) {
        Field[] fields = object_class.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (isInstanceOf(f.getType(), field_class))
                return f;
        }
        return null;
    }


    public static Object getMemberByType(Class object_class, Class field_class, Object object) {
        Field f = getFieldByType(object_class, field_class);
        try {
            if (f != null) {
                return f.get(object);
            }
        } catch (Exception ignored) {
        }
        return null;
		
		/*Field[] fields =object_class.getDeclaredFields();
		for(int i =0; i < fields.length; i++)
		{
			Field f = fields[i];
			f.setAccessible(true);
			if(f.getType().equals(field_class))
			{
				try
				{
					/*if (Modifier.isStatic(f.getModifiers()))
					{
						return f.get(null);
					}else*/
					/*{
						return f.get(object);
					}
				} catch (Exception e)
				{
				}
			}
		}
		return null;*/
    }


    public static Object[] getMemberArrayByType(Class type, Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getType().isArray()) {
                Class ofArray = f.getType().getComponentType();
                if (isInstanceOf(ofArray, type)) {
                    try {
                        return (Object[]) f.get(object);
                    } catch (Exception ignored) {

                    }
                }
            }
        }
        return null;
    }

    public static boolean isInstanceOf(Class clazz, Class possibleInstance) {
        if (clazz.isAssignableFrom(possibleInstance))
            return true;
        if (clazz.isPrimitive()) {
            try {
                if (clazz.getField("TYPE").get(null).equals(possibleInstance))
                    return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }
}
