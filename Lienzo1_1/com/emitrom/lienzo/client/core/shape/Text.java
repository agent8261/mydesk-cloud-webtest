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

package com.emitrom.lienzo.client.core.shape;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.ShapeFactory;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.types.TextMetrics;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.json.client.JSONObject;

/**
 * Text implementation for Canvas.
 */
public class Text extends Shape<Text>
{
    /**
     * Constructor. Creates an instance of text.
     * 
     * @param text
     */
    public Text(String text)
    {
        super(ShapeType.TEXT);

        LienzoGlobals globals = LienzoGlobals.getInstance();

        if (null == text)
        {
            text = "";
        }
        setText(text).setFontFamily(globals.getDefaultFontFamily()).setFontStyle(globals.getDefaultFontStyle()).setFontSize(globals.getDefaultFontSize());
    }

    /**
     * Constructor. Creates an instance of text.
     * 
     * @param text 
     * @param family font family
     * @param points font size
     */
    public Text(String text, String family, double points)
    {
        super(ShapeType.TEXT);

        LienzoGlobals globals = LienzoGlobals.getInstance();

        if (null == text)
        {
            text = "";
        }
        if ((null == family) || ((family = family.trim()).isEmpty()))
        {
            family = globals.getDefaultFontFamily();
        }
        if (points <= 0)
        {
            points = globals.getDefaultFontSize();
        }
        setText(text).setFontFamily(family).setFontStyle(globals.getDefaultFontStyle()).setFontSize(points);
    }

    /**
     * Constructor. Creates an instance of text.
     * 
     * @param text
     * @param family font family
     * @param style font style (bold, italic, etc)
     * @param points font size
     */
    public Text(String text, String family, String style, double points)
    {
        super(ShapeType.TEXT);

        LienzoGlobals globals = LienzoGlobals.getInstance();

        if (null == text)
        {
            text = "";
        }
        if ((null == family) || ((family = family.trim()).isEmpty()))
        {
            family = globals.getDefaultFontFamily();
        }
        if ((null == style) || ((style = style.trim()).isEmpty()))
        {
            style = globals.getDefaultFontStyle();
        }
        if (points <= 0)
        {
            points = globals.getDefaultFontSize();
        }
        setText(text).setFontFamily(family).setFontStyle(style).setFontSize(points);
    }

    protected Text(JSONObject node)
    {
        super(ShapeType.TEXT, node);
    }

    /**
     * Draws this text
     * 
     * @param context
     */
    @Override
    public void draw(Context2D context)
    {
        Attributes attr = getAttributes();

        String text = attr.getText();

        if ((null == text) || (text.isEmpty()))
        {
            return;
        }
        if (attr.isDefined(Attribute.TEXT_BASELINE))
        {
            context.setTextBaseline(attr.getTextBaseLine());
        }
        if (attr.isDefined(Attribute.TEXT_ALIGN))
        {
            context.setTextAlign(attr.getTextAlign());
        }
        context.setTextFont(attr.getFontStyle() + " " + attr.getFontSize() + "pt " + attr.getFontFamily());
    }

    @Override
    protected void fill(Context2D context, Attributes attr)
    {
        if (context.isSelection())
        {
            context.save();

            context.beginPath();

            context.setGlobalAlpha(1);

            context.setFillColor(getColorKey());

            context.fillText(attr.getText(), 0, 0);

            context.restore();

            return;
        }
        if (attr.isDefined(Attribute.FILL))
        {
            boolean apsh = false;

            context.save();

            context.beginPath();

            if ((attr.isDefined(Attribute.SHADOW)) && (m_apsh == false))
            {
                apsh = m_apsh = doApplyShadow(context, attr);
            }
            String fill = attr.getFillColor();

            if (apsh)
            {
                if (null != fill)
                {
                    context.setFillColor(fill);
                }
                else
                {
                    context.setFillColor(ColorName.WHITE);
                }
                context.fillText(attr.getText(), 0, 0);
            }
            else if (null != fill)
            {
                context.setFillColor(fill);

                context.fillText(attr.getText(), 0, 0);
            }
            context.restore();

            if (apsh)
            {
                fill(context, attr);
            }
        }
    }

    @Override
    protected void stroke(Context2D context, Attributes attr)
    {
        String color = attr.getStrokeColor();

        if (null != color)
        {
            boolean apsh = false;

            boolean selection = context.isSelection();

            if (selection)
            {
                color = getColorKey();

                apsh = false;
            }
            context.save();

            context.beginPath();

            if ((false == selection) && (attr.isDefined(Attribute.SHADOW)) && (m_apsh == false))
            {
                apsh = m_apsh = doApplyShadow(context, attr);
            }
            context.setStrokeColor(color);

            double width = attr.getStrokeWidth();

            if (width == 0)
            {
                width = 1;
            }
            context.setStrokeWidth(width);

            context.strokeText(attr.getText(), 0, 0);

            context.restore();

            if (apsh)
            {
                stroke(context, attr);
            }
        }
    }

    /**
     * Returns TextMetrics, which includes an approximate value for
     * height. As close as we can estimate it at this time.
     * 
     * @param context
     * @return TextMetric or null if the text is empty or null
     */
    public TextMetrics measure(Context2D context)
    {
        TextMetrics size = null;

        Attributes attr = getAttributes();

        String text = getText();

        if ((null == text) || (text.isEmpty()))
        {
            return size;
        }
        context.save();

        context.setTextAlign(TextAlign.LEFT);

        context.setTextBaseline(TextBaseLine.ALPHABETIC);

        context.setTextFont(attr.getFontStyle() + " " + attr.getFontSize() + "pt " + attr.getFontFamily());

        double width = attr.getStrokeWidth();

        if (width == 0)
        {
            width = 2;
        }
        context.setStrokeWidth(width);

        context.transform(getAbsoluteTransform());

        size = context.measureText(text);

        double height = context.measureText("M").getWidth();

        size.setHeight(height - height / 6);

        context.restore();

        return size;
    }

    /**
     * Returns the {@link Text} String
     * 
     * @return String
     */
    public String getText()
    {
        return getAttributes().getText();
    }

    /**
     * Sets the {@link Text} String
     * 
     * @return this Text
     */
    public Text setText(String text)
    {
        getAttributes().setText(text);

        return this;
    }

    /**
     * Returns the Font Family.
     * 
     * @return String
     */
    public String getFontFamily()
    {
        return getAttributes().getFontFamily();
    }

    /**
     * Sets the {@link Text} Font Family
     * 
     * @return this Text
     */
    public Text setFontFamily(String family)
    {
        getAttributes().setFontFamily(family);

        return this;
    }

    /**
     * Returns the Font Style.
     * 
     * @return String
     */
    public String getFontStyle()
    {
        return getAttributes().getFontStyle();
    }

    /**
     * Sets the Font Style.
     * 
     * @param style
     * @return this Text
     */
    public Text setFontStyle(String style)
    {
        getAttributes().setFontStyle(style);

        return this;
    }

    /**
     * Returns the Font Size.
     * 
     * @return double
     */
    public double getFontSize()
    {
        return getAttributes().getFontSize();
    }

    /**
     * Sets the Font Size.
     * 
     * @param size
     * @return this Text
     */
    public Text setFontSize(double size)
    {
        getAttributes().setFontSize(size);

        return this;
    }

    /**
     * Returns the {@link TextAlign}
     * 
     * @return {@link TextAlign}
     */
    public TextAlign getTextAlign()
    {
        return getAttributes().getTextAlign();
    }

    /**
     * Sets the {@link TextAlign}
     * 
     * @param align
     * @return this Text
     */
    public Text setTextAlign(TextAlign align)
    {
        getAttributes().setTextAlign(align);

        return this;
    }

    /**
     * Returns the {@link TextBaseLine}
     * 
     * @return {@link TextBaseLine}
     */
    public TextBaseLine getTextBaseLine()
    {
        return getAttributes().getTextBaseLine();
    }

    /**
     * Sets the {@link TextBaseLine}
     * 
     * @param baseline
     * @return this Text
     */
    public Text setTextBaseLine(TextBaseLine baseline)
    {
        getAttributes().setTextBaseLine(baseline);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new TextFactory();
    }

    public static class TextFactory extends ShapeFactory<Text>
    {
        public TextFactory()
        {
            super(ShapeType.TEXT);

            addAttribute(Attribute.TEXT, true);

            addAttribute(Attribute.FONT_SIZE);

            addAttribute(Attribute.FONT_STYLE);

            addAttribute(Attribute.FONT_FAMILY);

            addAttribute(Attribute.TEXT_ALIGN);

            addAttribute(Attribute.TEXT_BASELINE);
        }

        @Override
        public Text create(JSONObject node, ValidationContext ctx)
        {
            return new Text(node);
        }
    }
}
