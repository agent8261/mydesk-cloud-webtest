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

import com.emitrom.lienzo.client.core.animation.AnimationProperties;
import com.emitrom.lienzo.client.core.animation.AnimationTweener;
import com.emitrom.lienzo.client.core.animation.IAnimationCallback;
import com.emitrom.lienzo.client.core.animation.IAnimationHandle;
import com.emitrom.lienzo.client.core.types.DragBounds;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Transform;
import com.emitrom.lienzo.client.widget.DragConstraintEnforcer;
import com.emitrom.lienzo.shared.core.types.DragConstraint;

/**
 * A common interface for all primitives, i.e. {@link Shape} and {@link Group}. 
 */
public interface IPrimitive<T extends Node<T>> extends IDrawable<T>
{
    /**
     * Gets the X coordinate.
     * 
     * @return double
     */
    public double getX();

    /**
     * Sets the X coordinate.
     * 
     * @param x
     * @return this IPrimitive
     */
    public T setX(double x);

    /**
     * Gets the Y coordinate.
     * 
     * @return double
     */
    public double getY();

    /**
     * Sets the Y coordinate.
     * 
     * @param y
     * @return this IPrimitive
     */
    public T setY(double y);

    /**
     * Sets the X and Y attributes to P.x and P.y
     * 
     * @param p Point2D
     * @return this IPrimitive
     */
    public T setLocation(Point2D p);

    /**
     * Returns the X and Y attributes as a Point2D
     * 
     * @return Point2D
     */
    public Point2D getLocation();

    /**
     * Returns whether the node is listening (i.e. not ignoring) for events
     * 
     * @return true
     */
    public boolean isListening();

    /**
     * Sets whether the node will listen for events.
     * 
     * @param listening
     * @return this IPrimitive
     */
    public T setListening(boolean listening);

    /**
     * Returns whether this node is visible.
     * 
     * @return boolean
     */
    public boolean isVisible();

    /**
     * Sets whether this node is visible.
     * 
     * @param visible
     * @return this IPrimitive
     */
    public T setVisible(boolean visible);

    /**
     * Returns whether this node can be dragged.
     * 
     * @return boolean
     */
    public boolean isDraggable();

    /**
     * Sets whether this node can be dragged.
     * 
     * @param draggable
     * @return this IPrimitive
     */
    public T setDraggable(boolean draggable);

    /**
     * Gets this node's scale as a Point2D
     * 
     * @return Point2D
     */
    public Point2D getScale();

    /**
     * Sets the node's scale
     * 
     * @param scale Point2D
     * @return this IPrimitive
     */
    public T setScale(Point2D scale);

    /**
     * Sets the node's scale, with the same value for x and y
     * 
     * @param xy
     * @return this IPrimitive
     */
    public T setScale(double xy);

    /**
     * Sets the node's scale, with the given x and y coordinates
     * 
     * @param x
     * @param y
     * @return this IPrimitive
     */
    public T setScale(double x, double y);

    /**
     * Gets this node's rotation, in radians.
     * 
     * @return double
     */
    public double getRotation();

    /**
     * Sets this node's rotation, in radians.
     * 
     * @param radians
     * @return this IPrimitive
     */
    public T setRotation(double radians);

    /**
     * Gets this node's rotation, in degrees.
     * 
     * @return double
     */
    public double getRotationDegrees();

    /**
     * Sets this node's rotation, in degrees.
     * 
     * @param degrees
     * @return this IPrimitive
     */
    public T setRotationDegrees(double degrees);

    /**
     * Gets this node's shear as a {@link Point2D}
     * 
     * @return Point2D
     */
    public Point2D getShear();

    /**
     * Sets this node's shear
     * 
     * @param shear
     * @return this IPrimitive
     */
    public T setShear(Point2D shear);

    /**
     * Sets this node's shear
     * 
     * @param shear
     * @return this IPrimitive
     */
    public T setShear(double shearX, double shearY);

    /**
     * Gets this node's offset as a {@link Point2D}
     * 
     * @return Point2D
     */
    public Point2D getOffset();

    /**
     * Sets this node's offset
     * 
     * @param offset
     * @return this IPrimitive
     */
    public T setOffset(Point2D offset);

    /**
     * Sets this node's offset, with the same value for x and y.
     * 
     * @param xy
     * @return this IPrimitive
     */
    public T setOffset(double xy);

    /**
     * Sets this node's offset, at the given x and y coordinates.
     * 
     * @param x
     * @param y
     * @return this IPrimitive
     */
    public T setOffset(double x, double y);

    /**
     * Gets this node's {@link DragConstraint}
     * 
     * @return DragConstraint
     */
    public DragConstraint getDragConstraint();

    /**
     * Sets this node's drag constraint; e.g., horizontal, vertical or none (default)
     * 
     * @param constraint
     * @return this IPrimitive
     */
    public T setDragConstraint(DragConstraint constraint);

    /**
     * Gets the {@link DragBounds} for this node.
     * 
     * @return DragBounds
     */
    public DragBounds getDragBounds();

    /**
     * Sets this node's drag bounds.
     * 
     * @param bounds
     * @return this IPrimitive
     */
    public T setDragBounds(DragBounds bounds);

    /**
     * Gets the alpha for this node.
     * 
     * @return double
     */
    public double getAlpha();

    /**
     * Sets alpha
     * 
     * @param alpha
     * @return this IPrimitive
     */
    public T setAlpha(double alpha);

    /**
     * Gets the transform for this node.
     * 
     * @return Transform
     */
    public Transform getTransform();

    /**
     * Sets the Transform for this node.
     * 
     * @param transform
     * @return this IPrimitive
     */
    public T setTransform(Transform transform);

    /**
     * Animates this node using a tweening animation that runs for the specified duration.
     * <p>
     * Basically invokes {@link #animate(AnimationTweener, AnimationProperties, int, IAnimationCallback)} with a callback of <code>null</code>
     * See that method for more details.
     * 
     * @param tweener {@link AnimationTweener} - determines how the attributes will be changed over time
     * @param properties {@link AnimationProperties} - attributes that will be modified over time
     * @param duration in milliseconds
     * @return {@link IAnimationHandle}
     */
    public IAnimationHandle animate(AnimationTweener tweener, AnimationProperties properties, double duration /* milliseconds */);

    /**
     * Animates this node using a tweening animation that runs for the specified duration.
     * The attributes of this node are gradually modified over time. 
     * The tweener defines how the attributes are changed over time, e.g. LINEAR or not.
     * See {@link AttributeTweener} for the various non-linear transitions. 
     * <p>
     * If a callback is specified, it is called whenever the animation starts, ends and once for every animation frame.
     * 
     * @param tweener {@link AnimationTweener} - determines how the attributes will be changed over time
     * @param properties {@link AnimationProperties} - attributes that will be modified over time
     * @param duration in milliseconds
     * @param callback {@link IAnimationCallback}
     * @return {@link IAnimationHandle}
     * 
     * @see {@link AnimationManager#add(IPrimitive, AnimationTweener, AnimationProperties, int, IAnimationCallback)}
     */
    public IAnimationHandle animate(AnimationTweener tweener, AnimationProperties properties, double duration /* milliseconds */, IAnimationCallback callback);

    /**
     * Returns the parent Node.
     * 
     * @return Node
     */
    public Node<?> getParent();

    /**
     * Returns the DragConstraintEnforcer for this node.
     * This may adjust the node's location during drag operations.
     * The default implementation enforces the constraints defined by
     * the dragConstraint and dragBounds properties.
     * 
     * @return DragConstraintEnforcer
     */
    public DragConstraintEnforcer getDragConstraints();

    /**
     * Sets the DragConstraintEnforcer for this node.
     * This may adjust the node's location during drag operations.
     * The default implementation enforces the constraints defined by
     * the dragConstraint and dragBounds properties.
     * 
     * @param enforcer DragConstraintEnforcer
     */
    public void setDragConstraints(DragConstraintEnforcer enforcer);

    /**
     * Attaches all primitives to the Layers Color Map
     */
    public void attachToLayerColorMap();

    /**
     * Detaches all primitives from the Layers Color Map
     */
    public void detachFromLayerColorMap();
}