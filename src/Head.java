/**
 * Palm.java - a model for the palm of a hand
 */


import javax.media.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import javax.media.opengl.glu.GLUquadric;
import javax.media.opengl.glu.GLU;

/**
 * A model for the palm of a hand as a sphere scaled in one direction.
 * 
 * @author Tana Hungwe
 * @since Fall 2016
 */
public class Head extends Circular implements Displayable {

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
  
  public Head(final double radius, final GLUT glut) {
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
  public void initialize(final GL2 gl) 
  {    
	 // Instantiate GLU object and Texture object for texture mapping
	 GLU glu = new GLU();
	 // Source the texture image
	 Texture skin = null;
	  try {
	    skin = TextureIO.newTexture(new File("src/data/fly_body.jpg"), true);
	  }
	  catch (IOException e) {    
	    javax.swing.JOptionPane.showMessageDialog(null, e);
	  }
	  // GLU Quadric where we send the GLUT object and the Texture
	  GLUquadric sphere = glu.gluNewQuadric();
	  glu.gluQuadricDrawStyle(sphere, glu.GLU_FILL);
	  glu.gluQuadricTexture(sphere, true);
	  glu.gluQuadricNormals(sphere, glu.GLU_SMOOTH);
	  
    this.callListHandle = gl.glGenLists(1);
    // create head by scaling a sphere
    
    // Enable and Bind the texture to the sphere
    gl.glNewList(this.callListHandle, GL2.GL_COMPILE); 
    skin.enable(gl);
    skin.bind(gl);
    gl.glPushMatrix();
    // position this so that the sphere is drawn above the x-y plane, not at
    // the origin
    gl.glTranslated(0, 0, this.radius());
    gl.glScalef(1, 1, 1);
    this.glut().glutSolidSphere(this.radius(), 36, 18);
    gl.glPopMatrix();
    skin.disable(gl);
    gl.glEndList();
    glu.gluDeleteQuadric(sphere);
    // Dispose of any resources
  }
}
