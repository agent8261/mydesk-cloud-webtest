/*
   Copyright (c) 2012 Emitrom LLC. All rights reserved. 
   For licensing questions, please contact us at licensing@emitrom.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.emitrom.lienzo.client.core.types;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Simple, super-fast minimal native Map that by default uses a String as a key, but does not fully implement the Map interface
 * 
 * For our purposes, in benchmarking, this is 50-60% faster than HashMap
 */
public final class FastStringMap<V>
{
    private final FastStringMapJSO<V> m_jso;

    public FastStringMap()
    {
        m_jso = FastStringMapJSO.make().cast();
    }

    /**
     * Add <key, value> to the map.
     * @param key
     * @param value
     */
    public final void put(String key, V value)
    {
        m_jso.put(key, value);
    }

    /**
     * Get the value based on the key passed in.
     * @param key
     * @return
     */
    public final V get(String key)
    {
        return m_jso.get(key);
    }

    /**
     * Remove the value based on the key passed in as argument.
     * @param key
     */
    public final void remove(String key)
    {
        m_jso.remove(key);
    }

    private static final class FastStringMapJSO<V> extends JavaScriptObject
    {
        protected FastStringMapJSO()
        {

        }

        private static final JavaScriptObject make()
        {
            return JavaScriptObject.createObject();
        }

        public final native void put(String key, V value)
        /*-{
			this[key] = value;
        }-*/;

        public final native V get(String key)
        /*-{
			return this[key];
        }-*/;

        public final native void remove(String key)
        /*-{
			delete this[key];
        }-*/;
    }
}
