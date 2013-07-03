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

package com.emitrom.lienzo.client.core;

import com.emitrom.lienzo.client.core.types.ImageData;
import com.emitrom.lienzo.client.core.types.ImageDataPixelColor;
import com.emitrom.lienzo.client.core.types.ImageLoader.ImageJSO;
import com.emitrom.lienzo.client.core.types.LinearGradient;
import com.emitrom.lienzo.client.core.types.PatternGradient;
import com.emitrom.lienzo.client.core.types.RadialGradient;
import com.emitrom.lienzo.client.core.types.Shadow;
import com.emitrom.lienzo.client.core.types.TextMetrics;
import com.emitrom.lienzo.client.core.types.Transform;
import com.emitrom.lienzo.shared.core.types.Color;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.CompositeOperation;
import com.emitrom.lienzo.shared.core.types.IColor;
import com.emitrom.lienzo.shared.core.types.LineCap;
import com.emitrom.lienzo.shared.core.types.LineJoin;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

/**
 * Wrapper around a JSO that serves as a proxy to access the native capabilities of Canvas 2D.
 * @see {@link NativeContext2D} 
 */
public class Context2D
{
    private final NativeContext2D m_jso;

    public Context2D(NativeContext2D jso)
    {
        m_jso = jso;
    }

    public Context2D save()
    {
        m_jso.save();

        return this;
    }

    public Context2D restore()
    {
        m_jso.restore();

        return this;
    }

    public Context2D beginPath()
    {
        m_jso.beginPath();

        return this;
    }

    public Context2D closePath()
    {
        m_jso.closePath();

        return this;
    }

    public Context2D rect(double x, double y, double w, double h)
    {
        m_jso.rect(x, y, w, h);

        return this;
    }

    public Context2D fill()
    {
        m_jso.fill();

        return this;
    }

    public Context2D stroke()
    {
        m_jso.stroke();

        return this;
    }

    public Context2D setFillColor(String color)
    {
        m_jso.setFillColor(color);

        return this;
    }

    /**
     * Sets the fill color
     * 
     * @param color {@link ColorName} or {@link Color}
     * 
     * @return this Context2D
     */
    public Context2D setFillColor(IColor color)
    {
        m_jso.setFillColor(null == color ? null : color.getColorString());

        return this;
    }

    public Context2D arc(double x, double y, double radius, double startAngle, double endAngle, boolean anticlockwise)
    {
        m_jso.arc(x, y, radius, startAngle, endAngle, anticlockwise);

        return this;
    }

    public Context2D arc(double x, double y, double radius, double startAngle, double endAngle)
    {
        m_jso.arc(x, y, radius, startAngle, endAngle, false);

        return this;
    }

    public Context2D setStrokeColor(String color)
    {
        m_jso.setStrokeColor(color);

        return this;
    }

    /**
     * Sets the stroke color
     * 
     * @param color {@link ColorName} or {@link Color}
     * 
     * @return this Context2D
     */
    public Context2D setStrokeColor(IColor color)
    {
        m_jso.setStrokeColor(null == color ? null : color.getColorString());

        return this;
    }

    public Context2D setStrokeWidth(double width)
    {
        m_jso.setStrokeWidth(width);

        return this;
    }

    public Context2D setLineCap(LineCap linecap)
    {
        m_jso.setLineCap(linecap);

        return this;
    }

    public Context2D setLineJoin(LineJoin linejoin)
    {
        m_jso.setLineJoin(linejoin);

        return this;
    }

    public Context2D transform(double d0, double d1, double d2, double d3, double d4, double d5)
    {
        m_jso.transform(d0, d1, d2, d3, d4, d5);

        return this;
    }

    public Context2D moveTo(double x, double y)
    {
        m_jso.moveTo(x, y);

        return this;
    }

    public Context2D bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y)
    {
        m_jso.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y);

        return this;
    }

    public Context2D lineTo(double x, double y)
    {
        m_jso.lineTo(x, y);

        return this;
    }

    public Context2D setFillGradient(LinearGradient gradient)
    {
        m_jso.setFillGradient(gradient);

        return this;
    }

    public Context2D setFillGradient(RadialGradient gradient)
    {
        m_jso.setFillGradient(gradient);

        return this;
    }

    public Context2D quadraticCurveTo(double cpx, double cpy, double x, double y)
    {
        m_jso.quadraticCurveTo(cpx, cpy, x, y);

        return this;
    }

    public Context2D transform(Transform transform)
    {
        m_jso.transform(transform);

        return this;
    }

    public Context2D setTextFont(String font)
    {
        m_jso.setTextFont(font);

        return this;
    }

    public Context2D setTextBaseline(TextBaseLine baseline)
    {
        if (null != baseline)
        {
            m_jso.setTextBaseline(baseline.getValue());
        }
        return this;
    }

    public Context2D setTextAlign(TextAlign textAlign)
    {
        if (null != textAlign)
        {
            m_jso.setTextAlign(textAlign.getValue());
        }
        return this;
    }

    public Context2D fillText(String text, double x, double y)
    {
        m_jso.fillText(text, x, y);

        return this;
    }

    public Context2D strokeText(String text, double x, double y)
    {
        m_jso.strokeText(text, x, y);

        return this;
    }

    public Context2D setGlobalAlpha(double alpha)
    {
        m_jso.setGlobalAlpha(alpha);

        return this;
    }

    public Context2D translate(double x, double y)
    {
        m_jso.translate(x, y);

        return this;
    }

    public Context2D rotate(double rot)
    {
        m_jso.rotate(rot);

        return this;
    }

    public Context2D scale(double sx, double sy)
    {
        m_jso.scale(sx, sy);

        return this;
    }

    public Context2D clearRect(double x, double y, double wide, double high)
    {
        m_jso.clearRect(x, y, wide, high);

        return this;
    }

    public Context2D setFillGradient(PatternGradient gradient)
    {
        m_jso.setFillGradient(gradient);

        return this;
    }

    public Context2D setShadow(Shadow shadow)
    {
        m_jso.setShadow(shadow);

        return this;
    }

    public Context2D clip()
    {
        m_jso.clip();

        return this;
    }

    public Context2D resetClip()
    {
        m_jso.resetClip();

        return this;
    }

    public Context2D setMiterLimit(double limit)
    {
        m_jso.setMiterLimit(limit);

        return this;
    }

    public boolean isSupported(String feature)
    {
        return m_jso.isSupported(feature);
    }

    public boolean isPointInPath(double x, double y)
    {
        return m_jso.isPointInPath(x, y);
    }

    public ImageDataPixelColor getImageDataPixelColor(int x, int y)
    {
        return m_jso.getImageDataPixelColor(x, y);
    }

    public ImageData getImageData(int x, int y, int width, int height)
    {
        return m_jso.getImageData(x, y, width, height);
    }

    public Context2D putImageData(ImageData imageData, int x, int y)
    {
        m_jso.putImageData(imageData, x, y);

        return this;
    }

    public Context2D putImageData(ImageData imageData, int x, int y, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight)
    {
        m_jso.putImageData(imageData, x, y, dirtyX, dirtyY, dirtyWidth, dirtyHeight);

        return this;
    }

    public TextMetrics measureText(String text)
    {
        return m_jso.measureText(text);
    }

    public final Context2D setGlobalCompositeOperation(CompositeOperation operation)
    {
        m_jso.setGlobalCompositeOperation(operation);

        return this;
    }

    public boolean isSelection()
    {
        return false;
    }

    public boolean isDrag()
    {
        return false;
    }

    public NativeContext2D getJSO()
    {
        return m_jso;
    }

    public Context2D drawImage(ImageJSO image, double x, double y)
    {
        m_jso.drawImage(image, x, y);

        return this;
    }

    public Context2D drawImage(ImageJSO image, double x, double y, double w, double h)
    {
        m_jso.drawImage(image, x, y, w, h);

        return this;
    }

    public Context2D drawImage(ImageJSO image, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh)
    {
        m_jso.drawImage(image, sx, sy, sw, sh, dx, dy, dw, dh);

        return this;
    }

    public Context2D drawImage(Element image, double x, double y)
    {
        m_jso.drawImage(image, x, y);

        return this;
    }

    public Context2D drawImage(Element image, double x, double y, double w, double h)
    {
        m_jso.drawImage(image, x, y, w, h);

        return this;
    }

    public Context2D drawImage(Element image, double sx, double sy, double sw, double sh, double x, double y, double w, double h)
    {
        m_jso.drawImage(image, sx, sy, sw, sh, x, y, w, h);

        return this;
    }

    public static class GradientJSO extends JavaScriptObject
    {
        protected GradientJSO()
        {

        }

        public final native String getType()
        /*-{
			return this.type;
        }-*/;
    }
}
