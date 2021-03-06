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
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.NativeContext2D;
import com.emitrom.lienzo.client.core.event.OrientationChangeEvent;
import com.emitrom.lienzo.client.core.event.OrientationChangeHandler;
import com.emitrom.lienzo.client.core.event.ResizeChangeEvent;
import com.emitrom.lienzo.client.core.event.ResizeChangeHandler;
import com.emitrom.lienzo.client.core.event.ResizeEndEvent;
import com.emitrom.lienzo.client.core.event.ResizeEndHandler;
import com.emitrom.lienzo.client.core.event.ResizeStartEvent;
import com.emitrom.lienzo.client.core.event.ResizeStartHandler;
import com.emitrom.lienzo.client.core.event.ViewportTransformChangedEvent;
import com.emitrom.lienzo.client.core.event.ViewportTransformChangedHandler;
import com.emitrom.lienzo.client.core.mediator.IMediator;
import com.emitrom.lienzo.client.core.mediator.Mediators;
import com.emitrom.lienzo.client.core.shape.json.ContainerNodeFactory;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.JSONDeserializer;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationException;
import com.emitrom.lienzo.client.core.types.FastArrayList;
import com.emitrom.lienzo.client.core.types.INodeFilter;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Transform;
import com.emitrom.lienzo.shared.core.types.DataURLType;
import com.emitrom.lienzo.shared.core.types.NodeType;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Serves as a container for {@link Scene}
 * 
 * <ul>
 * <li>A {@link Viewport} contains three {@link Scene} (Main, Drag and Back Scene)</li>
 * <li>The main {@link Scene} can contain multiple {@link Layer}.</li>
 * </ul> 
 */
public class Viewport extends ContainerNode<Scene, Viewport> implements IJSONSerializable<Viewport>
{
    private int              m_wide    = 0;

    private int              m_high    = 0;

    private final DivElement m_element = Document.get().createDivElement();

    private final Scene      m_drag    = new Scene();

    private final Scene      m_main    = new Scene();

    private final Scene      m_back    = new Scene();

    private Mediators        m_mediators;

    /**
     * Constructor. Creates an instance of a viewport.
     * 
     * @param wide
     * @param high
     */
    public Viewport(int wide, int high)
    {
        super(NodeType.VIEWPORT);

        m_wide = wide;

        m_high = high;

        add(m_back);

        add(m_main);

        add(m_drag);

        m_drag.add(new DragLayer());

        m_mediators = new Mediators(this);

        // Zoom mediators rely on the Transform not being null.
        setTransform(new Transform());
    }

    protected Viewport(JSONObject node)
    {
        super(NodeType.VIEWPORT);
    }

    /**
     * Returns the viewport width in pixels.
     * 
     * @return int
     */
    public int getWidth()
    {
        return m_wide;
    }

    /**
     * Returns the viewport height in pixels.
     * 
     * @return int
     */
    public int getHeight()
    {
        return m_high;
    }

    /**
     * Returns the {@link DivElement}
     * 
     * @return {@link DivElement}
     */
    public DivElement getElement()
    {
        return m_element;
    }

    /**
     * Sets size of the {@link Viewport} in pixels
     * 
     * @param wide
     * @param high
     * @return Viewpor this viewport
     */
    public Viewport setPixelSize(int wide, int high)
    {
        m_wide = wide;

        m_high = high;

        m_element.getStyle().setWidth(wide, Unit.PX);

        m_element.getStyle().setHeight(high, Unit.PX);

        FastArrayList<Scene> scenes = getChildNodes();

        if (null != scenes)
        {
            int size = scenes.length();

            for (int i = 0; i < size; i++)
            {
                Scene scene = scenes.get(i);

                if (null != scene)
                {
                    scene.setPixelSize(wide, high);
                }
            }
        }

        return this;
    }

    /**
     * Adds a {@link Scene} to this viewport.
     * 
     * @param scene
     */
    @Override
    public void add(Scene scene)
    {
        if ((null != scene) && (LienzoGlobals.getInstance().isCanvasSupported()))
        {
            DivElement element = scene.getElement();

            scene.setPixelSize(m_wide, m_high);

            element.getStyle().setPosition(Position.ABSOLUTE);

            element.getStyle().setDisplay(Display.INLINE_BLOCK);

            getElement().appendChild(element);

            super.add(scene);
        }
    }

    public HandlerRegistration addOrientationChangeHandler(OrientationChangeHandler handler)
    {
        return addEnsureHandler(OrientationChangeEvent.TYPE, handler);
    }

    public HandlerRegistration addResizeStartHandler(ResizeStartHandler handler)
    {
        return addEnsureHandler(ResizeStartEvent.TYPE, handler);
    }

    public HandlerRegistration addResizeChangeHandler(ResizeChangeHandler handler)
    {
        return addEnsureHandler(ResizeChangeEvent.TYPE, handler);
    }

    public HandlerRegistration addResizeEndHandler(ResizeEndHandler handler)
    {
        return addEnsureHandler(ResizeEndEvent.TYPE, handler);
    }

    public void draw()
    {
        FastArrayList<Scene> scenes = getChildNodes();

        if (null != scenes)
        {
            int size = scenes.length();

            for (int i = 0; i < size; i++)
            {
                Scene scene = scenes.get(i);

                if (null != scene)
                {
                    scene.draw();
                }
            }
        }
    }

    /**
     * Returns the main Scene for the {@link Viewport}
     * 
     * @return {@link Scene}
     */
    @Override
    public Scene getScene()
    {
        return m_main;
    }

    /**
     * Sets the background layer
     * 
     * @param layer
     * @return this Viewport
     */
    public Viewport setBackgroundLayer(Layer layer)
    {
        m_back.removeAll();

        m_back.add(layer);

        return this;
    }

    /**
     * Returns the Drag Layer.
     * 
     * @return {@link Layer} 
     */
    public Layer getDraglayer()
    {
        return m_drag.getChildNodes().get(0);
    }

    @Override
    public Viewport getViewport()
    {
        return this;
    }

    /**
     * No-op; this method has no effect. Simply overriden but in reality Scenes will not be removed from this {@link Viewport}
     */
    @Override
    public void remove(Scene scene)
    {
    }

    /**
     * No-op; this method has no effect. Simply overriden but in reality Scenes will not be removed from this {@link Viewport}
     */
    @Override
    public void removeAll()
    {
    }

    /**
     * No-op.
     * 
     * @return this Viewport
     */
    @Override
    public Viewport moveUp()
    {
        return this;
    }

    /**
     * No-op.
     * 
     * @return this Viewport
     */
    @Override
    public Viewport moveDown()
    {
        return this;
    }

    /**
     * No-op.
     * 
     * @return this Viewport
     */
    @Override
    public Viewport moveToTop()
    {
        return this;
    }

    /**
     * No-op.
     * 
     * @return this Viewport
     */
    @Override
    public Viewport moveToBottom()
    {
        return this;
    }

    /**
     * Change the viewport's transform so that the specified area (in global or canvas coordinates)
     * is visible.
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void viewGlobalArea(double x, double y, double width, double height)
    {
        if (width <= 0 || height <= 0)
        {
            return;
        }

        Transform t = getTransform();

        if (null != t)
        {
            Point2D a = new Point2D(x, y);

            Point2D b = new Point2D(x + width, y + height);

            Transform inv = t.getInverse();

            inv.transform(a, a);

            inv.transform(b, b);

            x = a.getX();

            y = a.getY();

            width = b.getX() - x;

            height = b.getY() - y;
        }
        viewLocalArea(x, y, width, height);
    }

    /**
     * Change the viewport's transform so that the specified area (in local or world coordinates)
     * is visible.
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void viewLocalArea(double x, double y, double width, double height)
    {
        Transform t = Transform.createViewportTransform(x, y, width, height, m_wide, m_high);

        if (t != null)
        {
            setTransform(t);
        }
    }

    /**
     * Sets the Transform for this Viewport and fires a ZoomEvent
     * to any ZoomHandlers registered with this Viewport.
     * 
     * 
     * @param transform Transform
     * @return this Viewport
     */
    @Override
    public Viewport setTransform(Transform transform)
    {
        super.setTransform(transform);

        super.fireEvent(new ViewportTransformChangedEvent(this));

        return this;
    }

    /**
     * Returns a {@link JSONObject} representation of the {@link Viewport} with its {@link Attributes} as well as its children.
     * 
     * @return {@link JSONObject}
     */
    @Override
    public JSONObject toJSONObject()
    {
        JSONObject object = new JSONObject();

        object.put("type", new JSONString(getNodeType().getValue()));

        object.put("attributes", new JSONObject(getAttributes()));

        JSONArray children = new JSONArray();

        children.set(0, m_main.toJSONObject());

        object.put("children", children);

        return object;
    }

    private final Layer getBackgroundLayer()
    {
        FastArrayList<Layer> list = m_back.getChildNodes();

        if (list.length() > 0)
        {
            return list.get(0);
        }
        return null;
    }

    public final Shape<?> findShapeAtPoint(int x, int y)
    {
        if (isVisible())
        {
            return getScene().findShapeAtPoint(x, y);
        }
        return null;
    }

    /**
     * Fires the given GWT event.
     */
    public final void fireEvent(GwtEvent<?> event)
    {
        getScene().fireEvent(event);
    }

    public final String toDataURL()
    {
        return getScene().toDataURL();
    }

    public final String toDataURL(boolean includeBackgroundLayer)
    {
        if (includeBackgroundLayer)
        {
            return getScene().toDataURL(getBackgroundLayer());
        }
        else
        {
            return getScene().toDataURL();
        }
    }

    public final String toDataURL(DataURLType mimetype)
    {
        return getScene().toDataURL(mimetype);
    }

    public final String toDataURL(DataURLType mimetype, boolean includeBackgroundLayer)
    {
        if (includeBackgroundLayer)
        {
            return getScene().toDataURL(mimetype, getBackgroundLayer());
        }
        else
        {
            return getScene().toDataURL(mimetype);
        }
    }

    @Override
    public ArrayList<Node<?>> search(INodeFilter filter)
    {
        ArrayList<Node<?>> find = new ArrayList<Node<?>>();

        if (filter.matches(this))
        {
            find.add(this);
        }
        find.addAll(m_main.search(filter));

        return find;
    }

    @Override
    public Iterator<Scene> iterator()
    {
        return new ViewportIterator();
    }

    /**
     * Returns the {@link Mediators} for this viewport.
     * Mediators can be used to e.g. to add zoom operations.
     * 
     * @return Mediators
     */
    public Mediators getMediators()
    {
        return m_mediators;
    }

    /**
     * Add a mediator to the stack of {@link Mediators} for this viewport.
     * The one that is added last, will be called first.
     * 
     * Mediators can be used to e.g. to add zoom operations.
     * 
     * @param mediator IMediator
     */
    public void pushMediator(IMediator mediator)
    {
        m_mediators.push(mediator);
    }

    /**
     * Adds a ViewportTransformChangedHandler that will be notified whenever the Viewport's 
     * transform changes (probably due to a zoom or pan operation.)
     * 
     * @param handler ViewportTransformChangedHandler
     * @return HandlerRegistration
     */
    public HandlerRegistration addViewportTransformChangedHandler(ViewportTransformChangedHandler handler)
    {
        return addEnsureHandler(ViewportTransformChangedEvent.getType(), handler);
    }

    @Override
    public boolean isValidForContainer(IJSONSerializable<?> node)
    {
        return (node instanceof Scene);
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new ViewportFactory();
    }

    public static class ViewportFactory extends ContainerNodeFactory<Viewport>
    {
        public ViewportFactory()
        {
            super(NodeType.VIEWPORT);

            // For Viewports, the Transform is required (for other Nodes it's optional),
            // so override the requirednesss.
            addAttribute(Attribute.TRANSFORM, true);
        }

        @Override
        public Viewport create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            Viewport g = new Viewport(node);

            JSONDeserializer.getInstance().deserializeChildren(g, node, this, ctx);

            return g;
        }

        @Override
        public boolean isValidForContainer(IContainer<?> g, IJSONSerializable<?> node)
        {
            return g.isValidForContainer(node);
        }
    }

    private class ViewportIterator implements Iterator<Scene>
    {
        private int m_indx = 0;

        @Override
        public boolean hasNext()
        {
            return (m_indx != 1);
        }

        @Override
        public Scene next()
        {
            if (m_indx >= 1)
            {
                throw new NoSuchElementException();
            }
            m_indx++;

            return m_main;
        }

        @Override
        public void remove()
        {
            throw new IllegalStateException();
        }
    }

    private static class DragLayer extends Layer
    {
        private DragContext2D m_context;

        public DragLayer()
        {
            super();

            setVisible(true);

            setListening(false);
        }

        @Override
        public CanvasElement getCanvasElement()
        {
            CanvasElement element = null;

            if (LienzoGlobals.getInstance().isCanvasSupported())
            {
                element = super.getCanvasElement();

                if (null != element)
                {
                    if (null == m_context)
                    {
                        m_context = new DragContext2D(getNativeContext2D(element));
                    }
                }
            }
            return element;
        }

        @Override
        public void setPixelSize(int wide, int high)
        {
            if (LienzoGlobals.getInstance().isCanvasSupported())
            {
                super.setPixelSize(wide, high);

                CanvasElement element = getCanvasElement();

                element.setHeight(high);

                element.setWidth(wide);

                element.getStyle().setPosition(Position.ABSOLUTE);

                element.getStyle().setDisplay(Display.INLINE_BLOCK);
            }
        }

        @Override
        public Context2D getContext()
        {
            return m_context;
        }

        private static class DragContext2D extends Context2D
        {
            public DragContext2D(NativeContext2D jso)
            {
                super(jso);
            }

            @Override
            public boolean isDrag()
            {
                return true;
            }
        }
    }

}
