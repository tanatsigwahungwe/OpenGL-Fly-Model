/**
 * Thorax.java - a model for the Thorax of a hand
 */


import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl

/**
 * A model for the Thorax of a hand as a sphere scaled in one direction.
 * 
 * @author Tanatsigwa Hungwe
 */
public class Thorax extends Circular implements Displayable {

  /**
   * The OpenGL handle to the display list which contains all the components
   * which comprise this cylinder.
   */
  private int callListHandle;

  /**
   * Instantiates this object with the specified radius and OpenGL utility
   * toolkit object for drawing the sphere.
   * 
   * @param radius
   *          The radius of this object.
   * @param glut
   *          The OpenGL utility toolkit object for drawing the sphere.
   */
  public Thorax(final double radius, final GLUT glut) {
    super(radius, glut);
  }

  /**
   * {@inheritDoc}
   * 
   * @param gl
   *          {@inheritDoc}
   * @see edu.bu.cs.cs480.Displayable#draw(javax.media.opengl.GL)
   */
  @Override
  public void draw(GL2 gl) {
    gl.glCallList(this.callListHandle);
  }

  /**
   * Defines the OpenGL call list which draws a scaled sphere.
   * 
   * @param gl
   *          {@inheritDoc}
   * 
   * @see edu.bu.cs.cs480.Displayable#initialize(javax.media.opengl.GL)
   */
  @Override
  public void initialize(final GL2 gl) {
    this.callListHandle = gl.glGenLists(1);

    // create an ellipsoid for the Thorax by scaling a sphere
    gl.glNewList(this.callListHandle, GL2.GL_COMPILE);
    gl.glPushMatrix();
    // position this so that the sphere is drawn above the x-y plane, not at
    // the origin
    gl.glTranslated(0, 0, this.radius());
    gl.glScalef(1.6f, 0.7f, 1);
    this.glut().glutSolidSphere(this.radius(), 36, 18);
    gl.glPopMatrix();
    gl.glEndList();
  }

}
