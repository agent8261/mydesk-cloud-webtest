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

import com.emitrom.lienzo.client.core.types.Point2D.Point2DJSO;
import com.emitrom.lienzo.shared.core.types.Color;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.IColor;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wraps a Shadow JSO providing JS native access to color, blur and coordinates offset.
 */
public final class Shadow
{
    private final ShadowJSO m_jso;

    public Shadow(ShadowJSO jso)
    {
        m_jso = jso;
    }

    /**
     * Constructs a Shadow from a color (as a String), a blur and an offset (offx, offy).
     * 
     * @param color String
     * @param blur
     * @param offx
     * @param offy
     */
    public Shadow(String color, int blur, double offx, double offy)
    {
        this(ShadowJSO.make(color, blur, new Point2D(offx, offy).getJSO()));
    }

    /**
     * Constructs a Shadow from a color (i.e. {@link Color} or {@link ColorName}), 
     * a blur and an offset (offx, offy).
     * 
     * @param color {@link Color} or {@link ColorName}
     * @param blur
     * @param offx
     * @param offy
     */
    public Shadow(IColor color, int blur, double offx, double offy)
    {
        this(ShadowJSO.make(color.getColorString(), blur, new Point2D(offx, offy).getJSO()));
    }

    /**
     * Returns the color as a string.
     * @return String
     */
    public String getColor()
    {
        return m_jso.getColor();
    }
    
    /**
     * Sets the color as a string.
     * 
     * @param color String
     * @return this Shadow
     */
    public Shadow setColor(String color)
    {
        m_jso.setColor(color);
        
        return this;
    }
    
    /**
     * Sets the color as a {@link Color} or {@link ColorName}.
     * 
     * @param color {@link Color} or {@link ColorName}
     * @return this Shadow
     */
    public Shadow setColor(IColor color)
    {
        m_jso.setColor(color.getColorString());
        
        return this;
    }

    /**
     * Returns the blur.
     * @return String
     */
    public int getBlur()
    {
        return m_jso.getBlur();
    }
    
    /**
     * Sets the blur.
     * 
     * @param blur int
     * @return this Shadow
     */
    public Shadow setBlur(int blur)
    {
        m_jso.setBlur(blur);
        
        return this;
    }

    /**
     * Returns the offset as a Point2D.
     * @return Point2D
     */
    public Point2D getOffset()
    {
        return new Point2D(m_jso.getOffset());
    }
    
    /**
     * Sets the color as a string.
     * 
     * @param offset Point2D
     * @return this Shadow
     */
    public Shadow setOffset(Point2D offset)
    {
        m_jso.setOffset(offset.getJSO());
        
        return this;
    }
    
    public final ShadowJSO getJSO()
    {
        return m_jso;
    }

    public static final class ShadowJSO extends JavaScriptObject
    {
        static final native ShadowJSO make(String color, int blur, Point2DJSO offset)
        /*-{
			return {
				color : color,
				blur : blur,
				offset : offset
			};
        }-*/;

        protected ShadowJSO()
        {

        }
        
        public final native String getColor()
        /*-{
            return this.color;
        }-*/;
        
        public final native void setColor(String color)
        /*-{
            this.color = color;
        }-*/;
        
        public final native int getBlur()
        /*-{
            return this.blur;
        }-*/;
        
        public final native int setBlur(int blur)
        /*-{
            this.blur = blur;
        }-*/;
        
        public final native Point2DJSO getOffset()
        /*-{
            return this.offset;
        }-*/;
        
        public final native void setOffset(Point2DJSO offset)
        /*-{
            this.offset = offset;
        }-*/;
    }
}