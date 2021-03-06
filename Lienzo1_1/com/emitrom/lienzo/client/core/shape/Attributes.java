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

import java.util.ArrayList;
import java.util.Collection;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.types.DashArray;
import com.emitrom.lienzo.client.core.types.DashArray.DashArrayJSO;
import com.emitrom.lienzo.client.core.types.DragBounds;
import com.emitrom.lienzo.client.core.types.DragBounds.DragBoundsJSO;
import com.emitrom.lienzo.client.core.types.FillGradient;
import com.emitrom.lienzo.client.core.types.LinearGradient;
import com.emitrom.lienzo.client.core.types.LinearGradient.LinearGradientJSO;
import com.emitrom.lienzo.client.core.types.NativeInternalType;
import com.emitrom.lienzo.client.core.types.PatternGradient;
import com.emitrom.lienzo.client.core.types.PatternGradient.PatternGradientJSO;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Point2D.Point2DJSO;
import com.emitrom.lienzo.client.core.types.Point2DArray;
import com.emitrom.lienzo.client.core.types.RadialGradient;
import com.emitrom.lienzo.client.core.types.RadialGradient.RadialGradientJSO;
import com.emitrom.lienzo.client.core.types.Shadow;
import com.emitrom.lienzo.client.core.types.Shadow.ShadowJSO;
import com.emitrom.lienzo.client.core.types.Transform;
import com.emitrom.lienzo.client.core.types.Transform.TransformJSO;
import com.emitrom.lienzo.shared.core.types.ArrowType;
import com.emitrom.lienzo.shared.core.types.DragConstraint;
import com.emitrom.lienzo.shared.core.types.LineCap;
import com.emitrom.lienzo.shared.core.types.LineJoin;
import com.emitrom.lienzo.shared.core.types.SerializationMode;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;

public class Attributes extends JavaScriptObject
{
    protected Attributes()
    {

    }

    public static final native Attributes make()
    /*-{
		return {};
    }-*/;

    public final boolean isClearLayerBeforeDraw()
    {
        if (isDefined(Attribute.CLEAR_LAYER_BEFORE_DRAW))
        {
            return getBoolean(Attribute.CLEAR_LAYER_BEFORE_DRAW.getProperty());
        }
        return true;
    }

    public final void setClearLayerBeforeDraw(boolean clear)
    {
        put(Attribute.CLEAR_LAYER_BEFORE_DRAW.getProperty(), clear);
    }

    public final boolean isZoomable()
    {
        if (isDefined(Attribute.ZOOMABLE))
        {
            return getBoolean(Attribute.ZOOMABLE.getProperty());
        }
        return true;
    }

    public final void setZoomable(boolean zoomable)
    {
        put(Attribute.ZOOMABLE.getProperty(), zoomable);
    }

    public final void setFillColor(String fill)
    {
        if (null != fill && !fill.isEmpty())
        {
            put(Attribute.FILL.getProperty(), fill);
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final String getFillColor()
    {
        return getString(Attribute.FILL.getProperty());
    }

    public final void setFillGradient(LinearGradient gradient)
    {
        if (null != gradient)
        {
            put(Attribute.FILL.getProperty(), gradient.getJSO());
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final void setFillGradient(RadialGradient gradient)
    {
        if (null != gradient)
        {
            put(Attribute.FILL.getProperty(), gradient.getJSO());
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final void setFillGradient(PatternGradient gradient)
    {
        if (null != gradient)
        {
            put(Attribute.FILL.getProperty(), gradient.getJSO());
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final FillGradient getFillGradient()
    {
        JavaScriptObject obj = getObject(Attribute.FILL.getProperty());

        if (null == obj)
        {
            return null;
        }

        String type = getString("type", obj);

        if ("LinearGradient".equals(type))
        {
            return new LinearGradient((LinearGradientJSO) obj);
        }
        else if ("RadialGradient".equals(type))
        {
            return new RadialGradient((RadialGradientJSO) obj);
        }
        else if ("PatternGradient".equals(type))
        {
            return new PatternGradient((PatternGradientJSO) obj);
        }
        return null;
    }

    public final void setStrokeColor(String stroke)
    {
        if (null != stroke && !stroke.isEmpty())
        {
            put(Attribute.STROKE.getProperty(), stroke);
        }
        else
        {
            delete(Attribute.STROKE.getProperty());
        }
    }

    public final String getStrokeColor()
    {
        return getString(Attribute.STROKE.getProperty());
    }

    public final void setLineCap(LineCap lineCap)
    {
        if (null != lineCap)
        {
            put(Attribute.LINE_CAP.getProperty(), lineCap.getValue());
        }
        else
        {
            delete(Attribute.LINE_CAP.getProperty());
        }
    }

    public final LineCap getLineCap()
    {
        return LineCap.lookup(getString(Attribute.LINE_CAP.getProperty()));
    }

    public final void setLineJoin(LineJoin lineJoin)
    {
        if (null != lineJoin)
        {
            put(Attribute.LINE_JOIN.getProperty(), lineJoin.getValue());
        }
        else
        {
            delete(Attribute.LINE_JOIN.getProperty());
        }
    }

    public final LineJoin getLineJoin()
    {
        return LineJoin.lookup(getString(Attribute.LINE_JOIN.getProperty()));
    }

    public final void setMiterLimit(double limit)
    {
        put(Attribute.MITER_LIMIT.getProperty(), limit);
    }

    public final double getMiterLimit()
    {
        if (isDefined(Attribute.MITER_LIMIT))
        {
            return getDouble(Attribute.MITER_LIMIT.getProperty());
        }
        return 10;
    }

    public final void setStrokeWidth(double width)
    {
        put(Attribute.STROKE_WIDTH.getProperty(), width);
    }

    public final double getStrokeWidth()
    {
        double w = getDouble(Attribute.STROKE_WIDTH.getProperty());
        
        return w == 0 ? 1 : w; // default strokeWidth to 1 if not set
    }

    public final void setX(double x)
    {
        put(Attribute.X.getProperty(), x);
    }

    public final void setY(double y)
    {
        put(Attribute.Y.getProperty(), y);
    }

    public final void setVisible(boolean visible)
    {
        put(Attribute.VISIBLE.getProperty(), visible);
    }

    public final boolean isVisible()
    {
        if (isDefined(Attribute.VISIBLE))
        {
            return getBoolean(Attribute.VISIBLE.getProperty());
        }
        return true;
    }

    public final void setDraggable(boolean draggable)
    {
        put(Attribute.DRAGGABLE.getProperty(), draggable);
    }

    public final boolean isDraggable()
    {
        return getBoolean(Attribute.DRAGGABLE.getProperty());
    }

    public final void setListening(boolean listening)
    {
        put(Attribute.LISTENING.getProperty(), listening);
    }

    public final boolean isListening()
    {
        if (isDefined(Attribute.LISTENING))
        {
            return getBoolean(Attribute.LISTENING.getProperty());
        }
        return true;
    }

    public final void setName(String name)
    {
        if (null != name)
        {
            put(Attribute.NAME.getProperty(), name);
        }
        else
        {
            delete(Attribute.NAME.getProperty());
        }
    }

    public final void setDashArray(DashArray array)
    {
        if (null != array)
        {
            put(Attribute.DASH_ARRAY.getProperty(), array.getJSO());
        }
        else
        {
            delete(Attribute.DASH_ARRAY.getProperty());
        }
    }

    public final DashArray getDashArray()
    {
        JsArrayMixed dash = getArray(Attribute.DASH_ARRAY.getProperty());

        if (null != dash)
        {
            DashArrayJSO djso = dash.cast();

            return new DashArray(djso);
        }
        return new DashArray();
    }

    public final void setDragConstraint(DragConstraint constraint)
    {
        if (null != constraint)
        {
            put(Attribute.DRAG_CONSTRAINT.getProperty(), constraint.getValue());
        }
        else
        {
            delete(Attribute.DRAG_CONSTRAINT.getProperty());
        }
    }

    public final DragConstraint getDragConstraint()
    {
        return DragConstraint.lookup(getString(Attribute.DRAG_CONSTRAINT.getProperty()));
    }

    public final String getName()
    {
        return getString(Attribute.NAME.getProperty());
    }

    public final void setID(String id)
    {
        if (null != id)
        {
            put(Attribute.ID.getProperty(), id);
        }
        else
        {
            delete(Attribute.ID.getProperty());
        }
    }

    public final String getID()
    {
        return getString(Attribute.ID.getProperty());
    }

    public final void setRotation(double radians)
    {
        put(Attribute.ROTATION.getProperty(), radians);
    }

    public final double getRotation()
    {
        return getDouble(Attribute.ROTATION.getProperty());
    }

    public final void setRotationDegrees(double degrees)
    {
        put(Attribute.ROTATION.getProperty(), degrees * Math.PI / 180);
    }

    public final double getRotationDegrees()
    {
        return getDouble(Attribute.ROTATION.getProperty()) * 180 / Math.PI;
    }

    public final void setRadius(double radius)
    {
        put(Attribute.RADIUS.getProperty(), radius);
    }

    public final void setCornerRadius(double cornerRadius)
    {
        put(Attribute.CORNER_RADIUS.getProperty(), cornerRadius);
    }

    public final void setAlpha(double alpha)
    {
        if (alpha < 0)
        {
            alpha = 0;
        }
        if (alpha > 1)
        {
            alpha = 1;
        }
        put(Attribute.ALPHA.getProperty(), alpha);
    }

    public final void setScale(Point2D scale)
    {
        if (null != scale)
        {
            put(Attribute.SCALE.getProperty(), scale.getJSO());
        }
        else
        {
            delete(Attribute.SCALE.getProperty());
        }
    }

    public final void setScale(double scalex, double scaley)
    {
        setScale(new Point2D(scalex, scaley));
    }

    public final void setScale(double value)
    {
        setScale(new Point2D(value, value));
    }

    public final Point2D getScale()
    {
        JavaScriptObject scale = getObject(Attribute.SCALE.getProperty());

        if (null != scale)
        {
            Point2DJSO pjso = scale.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void setShear(double shearX, double shearY)
    {
        setShear(new Point2D(shearX, shearY));
    }

    public final void setShear(Point2D shear)
    {
        if (null != shear)
        {
            put(Attribute.SHEAR.getProperty(), shear.getJSO());
        }
        else
        {
            delete(Attribute.SHEAR.getProperty());
        }
    }

    public final Point2D getShear()
    {
        JavaScriptObject shear = getObject(Attribute.SHEAR.getProperty());

        if (null != shear)
        {
            Point2DJSO pjso = shear.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void setOffset(Point2D offset)
    {
        if (null != offset)
        {
            put(Attribute.OFFSET.getProperty(), offset.getJSO());
        }
        else
        {
            delete(Attribute.OFFSET.getProperty());
        }
    }

    public final void setOffset(double x, double y)
    {
        setOffset(new Point2D(x, y));
    }

    public final Point2D getOffset()
    {
        JavaScriptObject offset = getObject(Attribute.OFFSET.getProperty());

        if (null != offset)
        {
            Point2DJSO pjso = offset.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void setTransform(Transform transform)
    {
        if (null != transform)
        {
            put(Attribute.TRANSFORM.getProperty(), transform.getJSO());
        }
        else
        {
            delete(Attribute.TRANSFORM.getProperty());
        }
    }

    public final Transform getTransform()
    {
        JavaScriptObject xrfm = getArray(Attribute.TRANSFORM.getProperty());

        if (null != xrfm)
        {
            TransformJSO pjso = xrfm.cast();

            return new Transform(pjso);
        }
        return null;
    }

    public final void setWidth(double width)
    {
        put(Attribute.WIDTH.getProperty(), width);
    }

    public final void setHeight(double height)
    {
        put(Attribute.HEIGHT.getProperty(), height);
    }

    public final void setPoints(Point2DArray points)
    {
        if (null != points)
        {
            put(Attribute.POINTS.getProperty(), points.getJSO());
        }
        else
        {
            delete(Attribute.POINTS.getProperty());
        }
    }

    public final Point2DArray getPoints()
    {
        JsArray<JavaScriptObject> points = getArrayOfJSO(Attribute.POINTS.getProperty());

        if (null != points)
        {
            return new Point2DArray(points);
        }
        return new Point2DArray();
    }

    public final void setStarPoints(int points)
    {
        if (points < 5)
        {
            points = 5;
        }
        put(Attribute.STAR_POINTS.getProperty(), points);
    }

    public final void setText(String text)
    {
        if (null == text)
        {
            text = "";
        }
        put(Attribute.TEXT.getProperty(), text);
    }

    public final String getText()
    {
        String text = getString(Attribute.TEXT.getProperty());

        if (null == text)
        {
            text = "";
        }
        return text;
    }

    public final void setFontSize(double points)
    {
        if (points <= 0.0)
        {
            points = LienzoGlobals.getInstance().getDefaultFontSize();
        }
        put(Attribute.FONT_SIZE.getProperty(), points);
    }

    public final double getFontSize()
    {
        double points = getDouble(Attribute.FONT_SIZE.getProperty());

        if (points <= 0.0)
        {
            points = LienzoGlobals.getInstance().getDefaultFontSize();
        }
        return points;
    }

    public final void setSkew(double skew)
    {
        put(Attribute.SKEW.getProperty(), skew);
    }

    public final void setFontFamily(String family)
    {
        if ((null == family) || (family = family.trim()).isEmpty())
        {
            put(Attribute.FONT_FAMILY.getProperty(), LienzoGlobals.getInstance().getDefaultFontFamily());
        }
        else
        {
            put(Attribute.FONT_FAMILY.getProperty(), family);
        }
    }

    public final String getFontFamily()
    {
        String family = getString(Attribute.FONT_FAMILY.getProperty());

        if ((null == family) || (family = family.trim()).isEmpty())
        {
            family = LienzoGlobals.getInstance().getDefaultFontFamily();
        }
        return family;
    }

    public final void setFontStyle(String style)
    {
        if ((null == style) || (style = style.trim()).isEmpty())
        {
            put(Attribute.FONT_STYLE.getProperty(), LienzoGlobals.getInstance().getDefaultFontStyle());
        }
        else
        {
            put(Attribute.FONT_STYLE.getProperty(), style);
        }
    }

    public final String getFontStyle()
    {
        String style = getString(Attribute.FONT_STYLE.getProperty());

        if ((null == style) || (style = style.trim()).isEmpty())
        {
            style = LienzoGlobals.getInstance().getDefaultFontStyle();
        }
        return style;
    }

    public final void setTextBaseLine(TextBaseLine baseline)
    {
        if (null != baseline)
        {
            put(Attribute.TEXT_BASELINE.getProperty(), baseline.getValue());
        }
        else
        {
            delete(Attribute.TEXT_BASELINE.getProperty());
        }
    }

    public final void setTextAlign(TextAlign textAlign)
    {
        if (null != textAlign)
        {
            put(Attribute.TEXT_ALIGN.getProperty(), textAlign.getValue());
        }
        else
        {
            delete(Attribute.TEXT_ALIGN.getProperty());
        }
    }

    public final TextBaseLine getTextBaseLine()
    {
        return TextBaseLine.lookup(getString(Attribute.TEXT_BASELINE.getProperty()));
    }

    public final TextAlign getTextAlign()
    {
        return TextAlign.lookup(getString(Attribute.TEXT_ALIGN.getProperty()));
    }

    public final void setTextPadding(double padding)
    {
        put(Attribute.TEXT_PADDING.getProperty(), padding);
    }

    public final void setShadow(Shadow shadow)
    {
        if (null != shadow)
        {
            put(Attribute.SHADOW.getProperty(), shadow.getJSO());
        }
        else
        {
            delete(Attribute.SHADOW.getProperty());
        }
    }

    public final Shadow getShadow()
    {
        JavaScriptObject shadow = getObject(Attribute.SHADOW.getProperty());

        if (null != shadow)
        {
            ShadowJSO sjso = shadow.cast();

            return new Shadow(sjso);
        }
        return null;
    }

    public final void setStartAngle(double startAngle)
    {
        put(Attribute.START_ANGLE.getProperty(), startAngle);
    }

    public final void setEndAngle(double endAngle)
    {
        put(Attribute.END_ANGLE.getProperty(), endAngle);
    }

    public final void setCounterClockwise(boolean counterClockwise)
    {
        put(Attribute.COUNTER_CLOCKWISE.getProperty(), counterClockwise);
    }

    public final void setControlPoints(Point2DArray controlPoints)
    {
        if (null != controlPoints)
        {
            put(Attribute.CONTROL_POINTS.getProperty(), controlPoints.getJSO());
        }
        else
        {
            delete(Attribute.CONTROL_POINTS.getProperty());
        }
    }

    public final Point2DArray getControlPoints()
    {
        JsArray<JavaScriptObject> points = getArrayOfJSO(Attribute.CONTROL_POINTS.getProperty());

        if (null != points)
        {
            return new Point2DArray(points);
        }
        return new Point2DArray();
    }

    public final double getX()
    {
        return getDouble(Attribute.X.getProperty());
    }

    public final double getY()
    {
        return getDouble(Attribute.Y.getProperty());
    }

    public final double getRadius()
    {
        return getDouble(Attribute.RADIUS.getProperty());
    }

    public final double getCornerRadius()
    {
        return getDouble(Attribute.CORNER_RADIUS.getProperty());
    }

    public final double getWidth()
    {
        return getDouble(Attribute.WIDTH.getProperty());
    }

    public final double getHeight()
    {
        return getDouble(Attribute.HEIGHT.getProperty());
    }

    public final int getStarPoints()
    {
        int points = getInteger(Attribute.STAR_POINTS.getProperty());

        if (points < 5)
        {
            points = 5;
        }
        return points;
    }

    public final int getSides()
    {
        int sides = getInteger(Attribute.SIDES.getProperty());

        if (sides < 3)
        {
            sides = 3;
        }
        return sides;
    }

    public final void setSides(int sides)
    {
        if (sides < 3)
        {
            sides = 3;
        }
        put(Attribute.SIDES.getProperty(), sides);
    }

    public final double getStartAngle()
    {
        return getDouble(Attribute.START_ANGLE.getProperty());
    }

    public final double getEndAngle()
    {
        return getDouble(Attribute.END_ANGLE.getProperty());
    }

    public final boolean isCounterClockwise()
    {
        return getBoolean(Attribute.COUNTER_CLOCKWISE.getProperty());
    }

    public final double getSkew()
    {
        return getDouble(Attribute.SKEW.getProperty());
    }

    public final double getInnerRadius()
    {
        return getDouble(Attribute.INNER_RADIUS.getProperty());
    }

    public final void setInnerRadius(double radius)
    {
        put(Attribute.INNER_RADIUS.getProperty(), radius);
    }

    public final void setOuterRadius(double radius)
    {
        put(Attribute.OUTER_RADIUS.getProperty(), radius);
    }

    public final double getOuterRadius()
    {
        return getDouble(Attribute.OUTER_RADIUS.getProperty());
    }

    public final double getAlpha()
    {
        double alpha = getDouble(Attribute.ALPHA.getProperty());

        if (alpha < 0)
        {
            alpha = 0;
        }
        if (alpha > 1)
        {
            alpha = 1;
        }
        return alpha;
    }

    public final void setOffset(double xy)
    {
        setOffset(new Point2D(xy, xy));
    }

    public final DragBounds getDragBounds()
    {
        JavaScriptObject bounds = getObject(Attribute.DRAG_BOUNDS.getProperty());

        if (null != bounds)
        {
            DragBoundsJSO djso = bounds.cast();

            return new DragBounds(djso);
        }
        return null;
    }

    public final void setDragBounds(DragBounds bounds)
    {
        if (null != bounds)
        {
            put(Attribute.DRAG_BOUNDS.getProperty(), bounds.getJSO());
        }
        else
        {
            delete(Attribute.DRAG_BOUNDS.getProperty());
        }
    }

    public final void setClippedImageStartX(double clippedImageStartX)
    {
        put(Attribute.CLIPPED_IMAGE_START_X.getProperty(), clippedImageStartX);
    }

    public final double getClippedImageStartX()
    {
        return getDouble(Attribute.CLIPPED_IMAGE_START_X.getProperty());
    }

    public final void setClippedImageStartY(double clippedImageStartY)
    {
        put(Attribute.CLIPPED_IMAGE_START_Y.getProperty(), clippedImageStartY);
    }

    public final double getClippedImageStartY()
    {
        return getDouble(Attribute.CLIPPED_IMAGE_START_Y.getProperty());
    }

    public final void setClippedImageWidth(double clippedImageWidth)
    {
        put(Attribute.CLIPPED_IMAGE_WIDTH.getProperty(), clippedImageWidth);
    }

    public final double getClippedImageWidth()
    {
        return getDouble(Attribute.CLIPPED_IMAGE_WIDTH.getProperty());
    }

    public final void setClippedImageHeight(double clippedImageHeight)
    {
        put(Attribute.CLIPPED_IMAGE_HEIGHT.getProperty(), clippedImageHeight);
    }

    public final double getClippedImageHeight()
    {
        return getDouble(Attribute.CLIPPED_IMAGE_HEIGHT.getProperty());
    }

    public final void setClippedImageDestinationWidth(double clippedImageDestinationWidth)
    {
        put(Attribute.CLIPPED_IMAGE_DESTINATION_WIDTH.getProperty(), clippedImageDestinationWidth);
    }

    public final double getClippedImageDestinationWidth()
    {
        return getDouble(Attribute.CLIPPED_IMAGE_DESTINATION_WIDTH.getProperty());
    }

    public final void setClippedImageDestinationHeight(double clippedImageDestinationHeight)
    {
        put(Attribute.CLIPPED_IMAGE_DESTINATION_HEIGHT.getProperty(), clippedImageDestinationHeight);
    }

    public final double getClippedImageDestinationHeight()
    {
        return getDouble(Attribute.CLIPPED_IMAGE_DESTINATION_HEIGHT.getProperty());
    }

    public final void setPictureCategory(String pictureCategory)
    {
        put(Attribute.PICTURE_CATEGORY.getProperty(), pictureCategory);
    }

    public final String getPictureCategory()
    {
        return getString(Attribute.PICTURE_CATEGORY.getProperty());
    }

    public final void setResourceID(String resourceID)
    {
        put(Attribute.RESOURCE_ID.getProperty(), resourceID);
    }

    public final String getResourceID()
    {
        return getString(Attribute.RESOURCE_ID.getProperty());
    }

    public final void setSerializationMode(SerializationMode mode)
    {
        if (null != mode)
        {
            put(Attribute.SERIALIZATION_MODE.getProperty(), mode.getValue());
        }
        else
        {
            delete(Attribute.SERIALIZATION_MODE.getProperty());
        }
    }

    public final SerializationMode getSerializationMode()
    {
        return SerializationMode.lookup(getString(Attribute.SERIALIZATION_MODE.getProperty()));
    }

    public final void setBaseWidth(double baseWidth)
    {
        put(Attribute.BASE_WIDTH.getProperty(), baseWidth);
    }

    public final double getBaseWidth()
    {
        return getDouble(Attribute.BASE_WIDTH.getProperty());
    }

    public final void setHeadWidth(double headWidth)
    {
        put(Attribute.HEAD_WIDTH.getProperty(), headWidth);
    }

    public final double getHeadWidth()
    {
        return getDouble(Attribute.HEAD_WIDTH.getProperty());
    }

    public final void setArrowAngle(double arrowAngle)
    {
        put(Attribute.ARROW_ANGLE.getProperty(), arrowAngle);
    }

    public final double getArrowAngle()
    {
        return getDouble(Attribute.ARROW_ANGLE.getProperty());
    }

    public final void setBaseAngle(double baseAngle)
    {
        put(Attribute.BASE_ANGLE.getProperty(), baseAngle);
    }

    public final double getBaseAngle()
    {
        return getDouble(Attribute.BASE_ANGLE.getProperty());
    }

    public final void setArrowType(ArrowType arrowType)
    {
        if (null != arrowType)
        {
            put(Attribute.ARROW_TYPE.getProperty(), arrowType.getValue());
        }
        else
        {
            delete(Attribute.ARROW_TYPE.getProperty());
        }
    }

    public final ArrowType getArrowType()
    {
        return ArrowType.lookup(getString(Attribute.ARROW_TYPE.getProperty()));
    }

    public final void setURL(String url)
    {
        if (null != url)
        {
            put(Attribute.URL.getProperty(), url);
        }
        else
        {
            delete(Attribute.URL.getProperty());
        }
    }

    public final String getURL()
    {
        return getString(Attribute.URL.getProperty());
    }

    public final void setLoop(boolean loop)
    {
        put(Attribute.LOOP.getProperty(), loop);
    }

    public final boolean isLoop()
    {
        if (isDefined(Attribute.LOOP))
        {
            return getBoolean(Attribute.LOOP.getProperty());
        }
        return true;
    }

    public final void setVolume(double volume)
    {
        if (volume > 1.0)
        {
            volume = 1.0;
        }
        else if (volume < 0.0)
        {
            volume = 0.0;
        }
        put(Attribute.VOLUME.getProperty(), volume);
    }

    public final double getVolume()
    {
        if (isDefined(Attribute.VOLUME))
        {
            double volume = getDouble(Attribute.VOLUME.getProperty());

            if (volume > 1.0)
            {
                volume = 1.0;
            }
            else if (volume < 0.0)
            {
                volume = 0.0;
            }
            return volume;
        }
        return 0.0;
    }

    public final void put(String name, String value)
    {
        if (null != value)
        {
            put0(name, value.substring(0));
        }
        else
        {
            delete(name);
        }
    }

    public final native boolean isEmpty()
    /*-{
		var that = this;

		for ( var i in that) {
			return false;
		}
		return true;
    }-*/;

    private final native void put0(String name, String value)
    /*-{
		this[name] = value;
    }-*/;

    private final native void put(String name, int value)
    /*-{
		this[name] = value;
    }-*/;

    public final native void put(String name, double value)
    /*-{
		this[name] = value;
    }-*/;

    public final native void put(String name, boolean value)
    /*-{
		this[name] = value;
    }-*/;

    public final native void put(String name, JavaScriptObject value)
    /*-{
		this[name] = value;
    }-*/;

    public final Collection<String> getKeysCollection()
    {
        ArrayList<String> list = new ArrayList<String>();

        fillKeysCollection(list);

        return list;
    }

    private final native void fillKeysCollection(Collection<String> keys)
    /*-{
		var self = this;

		for ( var name in self) {
			if ((self.hasOwnProperty(name)) && (self[name] !== undefined)) {
				keys.@java.util.Collection::add(Ljava/lang/Object;)(name);
			}
		}
    }-*/;

    public final int getInteger(String name)
    {
        if (typeOf(name) == NativeInternalType.NUMBER)
        {
            return (int) (getDouble0(name) + 0.5);
        }
        return 0;
    }

    public final double getDouble(String name)
    {
        if (typeOf(name) == NativeInternalType.NUMBER)
        {
            return getDouble0(name);
        }
        return 0;
    }

    public final void putDouble(String name, double value)
    {
        put(name, value);
    }

    public final Point2D getPoint2D(String name)
    {
        JavaScriptObject offset = getObject(name);

        if (null != offset)
        {
            Point2DJSO pjso = offset.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void putPoint2D(String name, Point2D point)
    {
        if (null != point)
        {
            put(name, point.getJSO());
        }
        else
        {
            delete(Attribute.SCALE.getProperty());
        }
    }

    public final String getString(String name)
    {
        if (typeOf(name) == NativeInternalType.STRING)
        {
            return getString0(name);
        }
        return null;
    }

    public final boolean getBoolean(String name)
    {
        if (typeOf(name) == NativeInternalType.BOOLEAN)
        {
            return getBoolean0(name);
        }
        return false;
    }

    public final JavaScriptObject getObject(String name)
    {
        if (typeOf(name) == NativeInternalType.OBJECT)
        {
            return getObject0(name);
        }
        return null;
    }

    public final JsArray<JavaScriptObject> getArrayOfJSO(String name)
    {
        if (typeOf(name) == NativeInternalType.ARRAY)
        {
            return getArrayOfJSO0(name);
        }
        return null;
    }

    public final JsArrayMixed getArray(String name)
    {
        if (typeOf(name) == NativeInternalType.ARRAY)
        {
            return getArray0(name);
        }
        return null;
    }

    public final boolean isDefined(Attribute attr)
    {
        if (null == attr)
        {
            return false;
        }
        String prop = attr.getProperty();

        if (null == prop)
        {
            return false;
        }
        return isDefined0(prop);
    }

    private final native boolean isDefined0(String name)
    /*-{
		return this.hasOwnProperty(String(name));
    }-*/;

    private final native double getDouble0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native String getString0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native boolean getBoolean0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native JavaScriptObject getObject0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native JsArray<JavaScriptObject> getArrayOfJSO0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native JsArrayMixed getArray0(String name)
    /*-{
		return this[name];
    }-*/;

    public final native void delete(String name)
    /*-{
		delete this[name];
    }-*/;

    public final NativeInternalType typeOf(Attribute attr)
    {
        if (null != attr)
        {
            return typeOf(attr.getProperty());
        }
        return NativeInternalType.UNDEFINED;
    }

    public final native NativeInternalType typeOf(String name)
    /*-{
		if (this.hasOwnProperty(String(name)) && (this[name] !== undefined)) {

			var valu = this[name];

			var type = typeof valu;

			switch (type) {
			case 'string': {
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::STRING;
			}
			case 'boolean': {
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::BOOLEAN;
			}
			case 'number': {
				if (isFinite(valu)) {
					return @com.emitrom.lienzo.client.core.types.NativeInternalType::NUMBER;
				}
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::UNDEFINED;
			}
			case 'object': {
				if ((valu instanceof Array) || (valu instanceof $wnd.Array)) {
					return @com.emitrom.lienzo.client.core.types.NativeInternalType::ARRAY;
				}
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::OBJECT;
			}
			case 'function': {
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::FUNCTION;
			}
			}
		}
		return @com.emitrom.lienzo.client.core.types.NativeInternalType::UNDEFINED;
    }-*/;

    public static final native String getString(String name, JavaScriptObject obj)
    /*-{
		return obj[name];
    }-*/;
}