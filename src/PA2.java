import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;//for new version of gl
import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import javax.media.opengl.glu.GLUquadric;
import javax.media.opengl.glu.GLU;


/**
 * The main class which drives the body model simulation.
 * 
 * @author Tanatsigwa Hungwe <hungweta@bu.edu>
 * @since Fall 2016
 */
public class PA2 extends JFrame implements GLEventListener, KeyListener,
    MouseListener, MouseMotionListener {

	 //Leg
	  private class Leg {
	    /** The distal joint of this leg. */
	    private final Component distalJoint;
	    /** The list of all the joints in this leg. */
	    private final List<Component> joints;
	    /** The middle joint of this leg. */
	    private final Component middleJoint;
	    /** The thorax joint of this leg. */
	    private final Component thoraxJoint;

	    /**
	     * Instantiates this leg with the three specified joints.
	     * 
	     * @param thoraxJoint
	     *          The thorax joint of this leg.
	     * @param middleJoint
	     *          The middle joint of this leg.
	     * @param distalJoint
	     *          The distal joint of this leg.
	     */
	    public Leg(final Component thoraxJoint, final Component middleJoint,
	        final Component distalJoint) {
	      this.thoraxJoint = thoraxJoint;
	      this.middleJoint = middleJoint;
	      this.distalJoint = distalJoint;

	      this.joints = Collections.unmodifiableList(Arrays.asList(this.thoraxJoint,
	          this.middleJoint, this.distalJoint));
	    }

	    /**
	     * Gets the distal joint of this leg.
	     * 
	     * @return The distal joint of this leg.
	     */
	    Component distalJoint() {
	      return this.distalJoint;
	    }

	    /**
	     * Gets an unmodifiable view of the list of the joints of this leg.
	     * 
	     * @return An unmodifiable view of the list of the joints of this leg.
	     */
	    List<Component> joints() {
	      return this.joints;
	    }

	    /**
	     * Gets the middle joint of this leg.
	     * 
	     * @return The middle joint of this leg.
	     */
	    Component middleJoint() {
	      return this.middleJoint;
	    }

	    /**
	     * Gets the thorax joint of this leg.
	     * 
	     * @return The thorax joint of this leg.
	     */
	    Component thoraxJoint() {
	      return this.thoraxJoint;
	    }
	  }
	  
  
  /** The color for components which are selected for rotation. */
  public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
  /** The default width of the created window. */
  public static final int DEFAULT_WINDOW_HEIGHT = 512;
  /** The default height of the created window. */
  public static final int DEFAULT_WINDOW_WIDTH = 512;
  
  /** The radius of the body. */
  public static final double BODY_RADIUS = 0.5;
  /** The radius of the head. */
  public static final double HEAD_RADIUS = 0.25;
  /** The radius of the wing. */
  public static final double WING_RADIUS = 0.26;
  
  /** The height of the distal joint on each of the legs. */
  public static final double DISTAL_JOINT_HEIGHT = 0.2;
  /** The radius of each joint which comprises the leg. */
  public static final double FINGER_RADIUS = 0.06;
  /** The height of the middle joint on each of the legs. */
  public static final double MIDDLE_JOINT_HEIGHT = 0.25;
  /** The height of the thorax joint on each of the legs. */
  public static final double THORAX_JOINT_HEIGHT = 0.32;
  
  /** The color for components which are not selected for rotation. */
  public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
  /** The initial position of the top level component in the scene. */
  public static final Point3D INITIAL_POSITION = new Point3D(0, 0, 5);
  /** The angle by which to rotate the joint on user request to rotate. */
  public static final double ROTATION_ANGLE = 2.0;
  /** Randomly generated serial version UID. */
  private static final long serialVersionUID = -7060944143920496524L;

  /**
   * Runs the body simulation in a single JFrame.
   * 
   * @param args
   *          This parameter is ignored.
   */
  public static void main(final String[] args) {
    new PA2().animator.start();
  }

  /**
   * The animator which controls the framerate at which the canvas is animated.
   */
  final FPSAnimator animator;
  /** The canvas on which we draw the scene. */
  private final GLCanvas canvas;
  /** The capabilities of the canvas. */
  private final GLCapabilities capabilities = new GLCapabilities(null);
  /** The OpenGL utility object. */
  private final GLU glu = new GLU();
  /** The OpenGL utility toolkit object. */
  private final GLUT glut = new GLUT();
  
  //Legs
  /** The legs on the body to be modeled. */
  private final Leg[] legs;
  
  /** The body to be modeled. */
  private final Component body;
  
  /** The head to be modeled. */
  private final Component head;  
  
  /** The wing to be modeled. */
  private final Component wing; 
  
  /** The wing to be modeled. */
  private final Component wing2;  
  
  /** The wing joint to be modeled. */
  private final Component wing_joint; 
  
  /** The wing joint to be modeled. */
  private final Component wing_joint2; 

  /** The last x and y coordinates of the mouse press. */
  private int last_x = 0, last_y = 0;
  /** Whether the world is being rotated. */
  private boolean rotate_world = false;
  /** The axis around which to rotate the selected joints. */
  private Axis selectedAxis = Axis.X;
  /** The set of components which are currently selected for rotation. */
  private final Set<Component> selectedComponents = new HashSet<Component>(24);
  /**
   * The set of legs which have been selected for rotation.
   * 
   * Selecting a joint will only affect the joints in this set of selected
   * legs.
   **/
  private final Set<Leg> selectedLegs = new HashSet<Leg>(6);
  private boolean stateChanged = true;
  /**
   * The top level component in the scene which controls the positioning and
   * rotation of everything in the scene.
   */
  private final Component topLevelComponent;
  /** The quaternion which controls the rotation of the world. */
  private Quaternion viewing_quaternion = new Quaternion();
  /** The set of all components. */
  private final List<Component> components;

  public static String BODY_NAME = "body";
  public static String HEAD_NAME = "head";
  public static String WING_NAME = "wing";
  public static String WING_NAME2 = "wing2";
  public static String INDEX_THORAX_NAME = "index thorax";
  public static String INDEX_MIDDLE_NAME = "index middle";
  public static String INDEX_DISTAL_NAME = "index distal";
  public static String RING_THORAX_NAME = "ring thorax";
  public static String RING_MIDDLE_NAME = "ring middle";
  public static String RING_DISTAL_NAME = "ring distal";
  public static String MIDDLE_THORAX_NAME = "middle thorax";
  public static String MIDDLE_MIDDLE_NAME = "middle middle";
  public static String MIDDLE_DISTAL_NAME = "middle distal";
  public static String PINKY_THORAX_NAME = "pinky thorax";
  public static String PINKY_MIDDLE_NAME = "pinky middle";
  public static String PINKY_DISTAL_NAME = "pinky distal";
  public static String THUMB_THORAX_NAME = "thumb thorax";
  public static String THUMB_MIDDLE_NAME = "thumb middle";
  public static String THUMB_DISTAL_NAME = "thumb distal";
  public static String THUMB_THORAX_NAME2 = "thumb thorax2";
  public static String THUMB_MIDDLE_NAME2 = "thumb middle2";
  public static String THUMB_DISTAL_NAME2 = "thumb distal2";
  public static String WING_JOINT_NAME =  "wing_joint";
  public static String WING_JOINT_NAME2 =  "wing_joint2";
  public static String TOP_LEVEL_NAME = "top level";

  /**
   * Initializes the necessary OpenGL objects and adds a canvas to this JFrame.
   */
  public PA2() {
    this.capabilities.setDoubleBuffered(true);
    this.canvas = new GLCanvas(this.capabilities);
    this.canvas.addGLEventListener(this);
    this.canvas.addMouseListener(this);
    this.canvas.addMouseMotionListener(this);
    this.canvas.addKeyListener(this);
    // this is true by default, but we just add this line to be explicit
    this.canvas.setAutoSwapBufferMode(true);
    this.getContentPane().add(this.canvas);

    // refresh the scene at 60 frames per second
    this.animator = new FPSAnimator(this.canvas, 60);

    this.setTitle("CS480/CS680 : Bug Simulator");
    this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);

    //Model the whole Fly
    // the Body
    this.body = new Component(new Point3D(0, 0, 0), new Thorax(
        BODY_RADIUS, this.glut), BODY_NAME);

    // The Head
    this.head = new Component(new Point3D(-0.8, 0, 0.2), new Head(
        HEAD_RADIUS, this.glut), HEAD_NAME);
    
    // The Wings
    this.wing = new Component(new Point3D(0.97, 0.10, 0.27), new Wing(
            WING_RADIUS, this.glut), WING_NAME);
    this.wing2 = new Component(new Point3D(0.97, 0.10, -0.32), new Wing(
            WING_RADIUS, this.glut), WING_NAME2);
    
    //The Wing joint
    this.wing_joint = new Component(new Point3D(-0.37, 0.15,
            0.27), new RoundedCylinder(0.01,
                    0.5, this.glut), WING_JOINT_NAME);
    
    this.wing_joint2 = new Component(new Point3D(-0.37, 0.15,
            0.27), new RoundedCylinder(0.01,
                    0.5, this.glut), WING_JOINT_NAME2);
    
    // The Legs of the Fly
    // all the distal joints
    final Component distal1 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), PINKY_DISTAL_NAME);
    final Component distal2 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), RING_DISTAL_NAME);
     final Component distal3 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), MIDDLE_DISTAL_NAME);
        final Component distal4 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), INDEX_DISTAL_NAME);
    final Component distal5 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), THUMB_DISTAL_NAME);
    final Component distal6 = new Component(new Point3D(0, 0,
            0.27), new RoundedCylinder(FINGER_RADIUS,
            DISTAL_JOINT_HEIGHT, this.glut), THUMB_DISTAL_NAME2);
        
    // all the middle joints
    final Component middle1 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), PINKY_MIDDLE_NAME);
   final Component middle2 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), RING_MIDDLE_NAME);
    final Component middle3 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), MIDDLE_MIDDLE_NAME);
      final Component middle4 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), INDEX_MIDDLE_NAME);
        final Component middle5 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), THUMB_MIDDLE_NAME);
        final Component middle6 = new Component(new Point3D(0, 0,
        0.27), new RoundedCylinder(FINGER_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), THUMB_MIDDLE_NAME2);
        
    // all the thorax joints, displaced by various amounts from the thorax
    final Component thorax1 = new Component(new Point3D(-0.3, 0, 0.92),
        new RoundedCylinder(FINGER_RADIUS, THORAX_JOINT_HEIGHT, this.glut),
        PINKY_THORAX_NAME);
    final Component thorax2 = new Component(new Point3D(0, 0, 0.95),
        new RoundedCylinder(FINGER_RADIUS, THORAX_JOINT_HEIGHT, this.glut),
        RING_THORAX_NAME);
    final Component thorax3 = new Component(new Point3D(0.3, 0, 0.92),
        new RoundedCylinder(FINGER_RADIUS, THORAX_JOINT_HEIGHT, this.glut),
        MIDDLE_THORAX_NAME);
      final Component thorax4 = new Component(new Point3D(-0.3, 0, 0.1),
        new RoundedCylinder(FINGER_RADIUS, THORAX_JOINT_HEIGHT, this.glut),
        INDEX_THORAX_NAME);
      final Component thorax5 = new Component(new Point3D(0, 0, 0.11),
        new RoundedCylinder(FINGER_RADIUS, THORAX_JOINT_HEIGHT, this.glut),
        THUMB_THORAX_NAME);
      final Component thorax6 = new Component(new Point3D(0.3, 0, 0.1),
    	new RoundedCylinder(FINGER_RADIUS, THORAX_JOINT_HEIGHT, this.glut),
    	THUMB_THORAX_NAME2);
      
    // put together the legs for easier selection by keyboard input later on
    this.legs = new Leg[] { new Leg(thorax1, middle1, distal1),
    		new Leg(thorax2, middle2, distal2),
    		new Leg(thorax3, middle3, distal3),
    		new Leg(thorax4, middle4, distal4),
    		new Leg(thorax5, middle5, distal5), 
    		new Leg(thorax6, middle6, distal6), 
    		};
    
    // the top level component which provides an initial position and rotation
    // to the scene (but does not cause anything to be drawn)
    this.topLevelComponent = new Component(INITIAL_POSITION, TOP_LEVEL_NAME);
    this.topLevelComponent.addChildren(this.body);
    this.body.addChildren(this.head, wing_joint, wing_joint2, thorax1, thorax2, thorax3, thorax4, thorax5, thorax6);
    thorax1.addChild(middle1);
    middle1.addChild(distal1);
    thorax2.addChild(middle2);
    middle2.addChild(distal2);
    thorax3.addChild(middle3);
    middle3.addChild(distal3);
    thorax4.addChild(middle4);
    middle4.addChild(distal4);
    thorax5.addChild(middle5);
    middle5.addChild(distal5);
    thorax6.addChild(middle6);
    middle6.addChild(distal6);
    wing_joint.addChild(wing);
    wing_joint2.addChild(wing2);
    
    // Initial angle to see the Fly
    this.topLevelComponent.rotate(Axis.Y, 70);
    this.topLevelComponent.rotate(Axis.X, 30);
    
    // Right legs of the fly
    thorax4.rotate(Axis.Y, 180);
    thorax5.rotate(Axis.Y, 180);
    thorax6.rotate(Axis.Y, 180);
    thorax4.rotate(Axis.Z, 180);
    thorax5.rotate(Axis.Z, 180);
    thorax6.rotate(Axis.Z, 180);
    
    // Set Rotation Limits
    // For Head
    this.head.setXPositiveExtent(0);
    this.head.setXNegativeExtent(0);
    this.head.setYPositiveExtent(0);
    this.head.setYNegativeExtent(0);
    this.head.setZPositiveExtent(0);
    this.head.setZNegativeExtent(0);
    
    // For Body
    this.body.setXPositiveExtent(360);
    this.body.setXNegativeExtent(-360);
    this.body.setYPositiveExtent(360);
    this.body.setYNegativeExtent(-360);
    this.body.setZPositiveExtent(360);
    this.body.setZNegativeExtent(-360);
    
    // For Body/Leg Joints
    for (final Component thoraxJoint : Arrays.asList(thorax1, thorax2, thorax3)) {
      thoraxJoint.setXPositiveExtent(80);
      thoraxJoint.setXNegativeExtent(-10);
      thoraxJoint.setYPositiveExtent(16);
      thoraxJoint.setYNegativeExtent(-16);
      thoraxJoint.setZPositiveExtent(0);
      thoraxJoint.setZNegativeExtent(0);
    }
    
    for (final Component thoraxJoint : Arrays.asList(thorax4, thorax5, thorax6)) {
        thoraxJoint.setXPositiveExtent(10);
        thoraxJoint.setXNegativeExtent(-80);
        thoraxJoint.setYPositiveExtent(196);
        thoraxJoint.setYNegativeExtent(164);
        thoraxJoint.setZPositiveExtent(0);
        thoraxJoint.setZNegativeExtent(0);
      }
    
    // For Middle Joints
    for (final Component middleJoint : Arrays.asList(middle1, middle2,
        middle3)) {
      middleJoint.setXPositiveExtent(70);
      middleJoint.setXNegativeExtent(0);
      middleJoint.setYPositiveExtent(0);
      middleJoint.setYNegativeExtent(0);
      middleJoint.setZPositiveExtent(0);
      middleJoint.setZNegativeExtent(0);
    }
    
    for (final Component middleJoint : Arrays.asList(middle4, middle5,
            middle6)) {
          middleJoint.setXPositiveExtent(0);
          middleJoint.setXNegativeExtent(-70);
          middleJoint.setYPositiveExtent(0);
          middleJoint.setYNegativeExtent(0);
          middleJoint.setZPositiveExtent(0);
          middleJoint.setZNegativeExtent(0);
    }
    
    // For End Joints
    for (final Component distalJoint : Arrays.asList(distal1, distal2,
        distal3)) {
      distalJoint.setXPositiveExtent(8);
      distalJoint.setXNegativeExtent(-5);
      distalJoint.setYPositiveExtent(0);
      distalJoint.setYNegativeExtent(0);
      distalJoint.setZPositiveExtent(0);
      distalJoint.setZNegativeExtent(0);
    }
    
    for (final Component distalJoint : Arrays.asList(distal4, distal5,
            distal6)) {
          distalJoint.setXPositiveExtent(5);
          distalJoint.setXNegativeExtent(-8);
          distalJoint.setYPositiveExtent(0);
          distalJoint.setYNegativeExtent(0);
          distalJoint.setZPositiveExtent(0);
          distalJoint.setZNegativeExtent(0);
        }
    
    // Wing Joints
    this.wing_joint.setXPositiveExtent(0);
    this.wing_joint.setXNegativeExtent(0);
    this.wing_joint.setYPositiveExtent(10);
    this.wing_joint.setYNegativeExtent(-10);
    this.wing_joint.setZPositiveExtent(25);
    this.wing_joint.setZNegativeExtent(0);
    
    this.wing_joint2.setXPositiveExtent(0);
    this.wing_joint2.setXNegativeExtent(0);
    this.wing_joint2.setYPositiveExtent(10);
    this.wing_joint2.setYNegativeExtent(-10);
    this.wing_joint2.setZPositiveExtent(25);
    this.wing_joint2.setZNegativeExtent(0);
    
   // For Wings
    this.wing.setXPositiveExtent(5);
    this.wing.setXNegativeExtent(-8);
    this.wing.setYPositiveExtent(0);
    this.wing.setYNegativeExtent(0);
    this.wing.setZPositiveExtent(5);
    this.wing.setZNegativeExtent(0);
    
    this.wing2.setXPositiveExtent(5);
    this.wing2.setXNegativeExtent(-8);
    this.wing2.setYPositiveExtent(0);
    this.wing2.setYNegativeExtent(0);
    this.wing2.setZPositiveExtent(5);
    this.wing2.setZNegativeExtent(0);
    
    
    // create the list of all the components for debugging purposes
    this.components = Arrays.asList(this.body, this.head, thorax1, middle1, distal1,
    		thorax2, middle2, distal2, thorax3, middle3, distal3, thorax4, middle4, distal4,
    		thorax5, middle5, distal5, thorax6, middle6, distal6, this.wing_joint, this.wing_joint2,
    		this.wing, this.wing2);
  }
  /**
   * Redisplays the scene containing the body model.
   * 
   * @param drawable
   *          The OpenGL drawable object with which to create OpenGL models.
   */
  public void display(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // clear the display
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // from here on affect the model view
    gl.glMatrixMode(GL2.GL_MODELVIEW);

    // start with the identity matrix initially
    gl.glLoadIdentity();  

    // rotate the world by the appropriate rotation quaternion
    gl.glMultMatrixf(this.viewing_quaternion.toMatrix(), 0);

    // update the position of the components which need to be updated
    // TODO only need to update the selected and JUST deselected components
    if (this.stateChanged) {
      this.topLevelComponent.update(gl);
      this.stateChanged = false;
    }

    // redraw the components
    this.topLevelComponent.draw(gl);
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param drawable
   *          This parameter is ignored.
   * @param modeChanged
   *          This parameter is ignored.
   * @param deviceChanged
   *          This parameter is ignored.
   */
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged) {
    // intentionally unimplemented
  }

  /**
   * Initializes the scene and model.
   * 
   * @param drawable
   *          {@inheritDoc}
   */
  public void init(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // perform any initialization needed by the body model
    this.topLevelComponent.initialize(gl);

    // initially draw the scene
    this.topLevelComponent.update(gl);

    // set up for shaded display of the body
    final float light0_position[] = { 1, 1, 1, 0 };
    final float light0_ambient_color[] = { 0.25f, 0.25f, 0.25f, 1 };
    final float light0_diffuse_color[] = { 1, 1, 1, 1 };

    gl.glPolygonMode(GL.GL_FRONT, GL2.GL_FILL);
    gl.glEnable(GL2.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glShadeModel(GL2.GL_SMOOTH);

    // set up the light source
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_position, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light0_ambient_color, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse_color, 0);

    // turn lighting and depth buffering on
    gl.glEnable(GL2.GL_LIGHTING);
    gl.glEnable(GL2.GL_LIGHT0);
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_NORMALIZE);
  }

  /**
   * Interprets key presses according to the following scheme:
   * 
   * up-arrow, down-arrow: increase/decrease rotation angle
   * 
   * @param key
   *          The key press event object.
   */
  public void keyPressed(final KeyEvent key) {
    switch (key.getKeyCode()) {
    case KeyEvent.VK_KP_UP:
    case KeyEvent.VK_UP:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    case KeyEvent.VK_KP_DOWN:
    case KeyEvent.VK_DOWN:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, -ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    default:
      break;
    }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param key
   *          This parameter is ignored.
   */
  public void keyReleased(final KeyEvent key) {
    // intentionally unimplemented
  }

  private final TestCases testCases = new TestCases();

  private void setModelState(final Map<String, Angled> state) {
    this.body.setAngles(state.get(BODY_NAME));
    this.head.setAngles(state.get(HEAD_NAME));
    this.wing_joint.setAngles(state.get(WING_JOINT_NAME));
    this.wing_joint2.setAngles(state.get(WING_JOINT_NAME2));
    this.legs[0].thoraxJoint().setAngles(state.get(PINKY_THORAX_NAME));
    this.legs[0].middleJoint().setAngles(state.get(PINKY_MIDDLE_NAME));
    this.legs[0].distalJoint().setAngles(state.get(PINKY_DISTAL_NAME));
    this.legs[1].thoraxJoint().setAngles(state.get(RING_THORAX_NAME));
    this.legs[1].middleJoint().setAngles(state.get(RING_MIDDLE_NAME));
    this.legs[1].distalJoint().setAngles(state.get(RING_DISTAL_NAME));
    this.legs[2].thoraxJoint().setAngles(state.get(MIDDLE_THORAX_NAME));
    this.legs[2].middleJoint().setAngles(state.get(MIDDLE_MIDDLE_NAME));
    this.legs[2].distalJoint().setAngles(state.get(MIDDLE_DISTAL_NAME));
    this.legs[3].thoraxJoint().setAngles(state.get(INDEX_THORAX_NAME));
    this.legs[3].middleJoint().setAngles(state.get(INDEX_MIDDLE_NAME));
    this.legs[3].distalJoint().setAngles(state.get(INDEX_DISTAL_NAME));
    this.legs[4].thoraxJoint().setAngles(state.get(THUMB_THORAX_NAME));
    this.legs[4].middleJoint().setAngles(state.get(THUMB_MIDDLE_NAME));
    this.legs[4].distalJoint().setAngles(state.get(THUMB_DISTAL_NAME));
    this.legs[5].thoraxJoint().setAngles(state.get(THUMB_THORAX_NAME2));
    this.legs[5].middleJoint().setAngles(state.get(THUMB_MIDDLE_NAME2));
    this.legs[5].distalJoint().setAngles(state.get(THUMB_DISTAL_NAME2));
    this.wing.setAngles(state.get(WING_NAME));
    this.wing2.setAngles(state.get(WING_NAME2));

    this.stateChanged = true;
  }

  /**
   * Interprets typed keys according to the following scheme:
   * 
   * 0 : toggle the first leg (thumb) active in rotation
   * 
   * 1 : toggle the second leg active in rotation
   * 
   * 2 : toggle the third leg active in rotation
   * 
   * 3 : toggle the fourth leg active in rotation
   * 
   * 4 : toggle the fifth leg active in rotation
   * 
   * 5 : toggle the sixth leg active in rotation
   * 
   * 6 : toggle the body for rotation
   * 
   * X : use the X axis rotation at the active joint(s)
   * 
   * Y : use the Y axis rotation at the active joint(s)
   * 
   * Z : use the Z axis rotation at the active joint(s)
   * 
   * C : resets the body to the stop sign
   * 
   * T : select joint that connects leg to body
   * 
   * M : select middle joint
   * 
   * D : select last (distal) joint
   * 
   * R : resets the view to the initial rotation
   * 
   * K : prints the angles of the five legs for debugging purposes
   * 
   * l : select the joint for left wing
   * 
   * ; : select the joint for right wing
   * 
   * w : select the left wing
   * 
   * e : select the right wing
   * 
   * h : select the head
   * 
   * P : Cycles through poses
   * 
   * Q, Esc : exits the program
   * 
   */
  public void keyTyped(final KeyEvent key) {
    switch (key.getKeyChar()) {
    case 'Q':
    case 'q':
    case KeyEvent.VK_ESCAPE:
      new Thread() {
        @Override
        public void run() {
          PA2.this.animator.stop();
        }
      }.start();
      System.exit(0);
      break;
      
      // print the angles of the components
    case 'K':
    case 'k':
      printJoints();
      break;

    // resets to the stop sign
    case 'C':
    case 'c':
      this.setModelState(this.testCases.stop());
      break;

    // set the state of the body to the next test case
    case 'P':
    case 'p':
    	this.setModelState(this.testCases.next());
    	break;

    // set the viewing quaternion to 0 rotation
    case 'R':
    case 'r':
      this.viewing_quaternion.reset();
      break;

    // Toggle which leg(s) are affected by the current rotation
    case '0':
        toggleSelection(this.legs[0]);
        break;
    case '1':
      toggleSelection(this.legs[1]);
      break;
    case '2':
      toggleSelection(this.legs[2]);
      break;
    case '3':
      toggleSelection(this.legs[3]);
      break;
    case '4':
      toggleSelection(this.legs[4]);
      break;
    case '5':
      toggleSelection(this.legs[5]);
      break;

    // toggle which joints are affected by the current rotation
    case 'D':
    case 'd':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.distalJoint());
      }
      break;
    case 'M':
    case 'm':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.middleJoint());
      }
      break;
    case 'T':
    case 't':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.thoraxJoint());
      }
      break;

      // Body
    case '6':
      toggleSelection(this.body);
      break;
      
      // Wing1
    case 'w':
      toggleSelection(wing);
      break;
      
      // Wing2
    case 'e':
      toggleSelection(wing2);
      break;
      
      // Wing joint
    case 'l':
      toggleSelection(wing_joint);
      break;

      // Wing joint2
    case ';':
      toggleSelection(wing_joint2);
      break;
      
      // Head
    case 'h':
      toggleSelection(head);
      break;

    // change the axis of rotation at current active joint
    case 'X':
    case 'x':
      this.selectedAxis = Axis.X;
      break;
    case 'Y':
    case 'y':
      this.selectedAxis = Axis.Y;
      break;
    case 'Z':
    case 'z':
      this.selectedAxis = Axis.Z;
      break;
    default:
      break;
    }
  }

  /**
   * Prints the joints on the System.out print stream.
   */
  private void printJoints() {
    this.printJoints(System.out);
  }

  /**
   * Prints the joints on the specified PrintStream.
   * 
   * @param printStream
   *          The stream on which to print each of the components.
   */
  private void printJoints(final PrintStream printStream) {
    for (final Component component : this.components) {
      printStream.println(component);
    }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseClicked(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Updates the rotation quaternion as the mouse is dragged.
   * 
   * @param mouse
   *          The mouse drag event object.
   */
  public void mouseDragged(final MouseEvent mouse) {
	if (this.rotate_world) {
		// get the current position of the mouse
		final int x = mouse.getX();
		final int y = mouse.getY();
	
		// get the change in position from the previous one
		final int dx = x - this.last_x;
		final int dy = y - this.last_y;
	
		// create a unit vector in the direction of the vector (dy, dx, 0)
		final double magnitude = Math.sqrt(dx * dx + dy * dy);
		final float[] axis = magnitude == 0 ? new float[]{1,0,0}: // avoid dividing by 0
			new float[] { (float) (dy / magnitude),(float) (dx / magnitude), 0 };
	
		// calculate appropriate quaternion
		final float viewing_delta = 3.1415927f / 180.0f;
		final float s = (float) Math.sin(0.5f * viewing_delta);
		final float c = (float) Math.cos(0.5f * viewing_delta);
		final Quaternion Q = new Quaternion(c, s * axis[0], s * axis[1], s
				* axis[2]);
		this.viewing_quaternion = Q.multiply(this.viewing_quaternion);
	
		// normalize to counteract acccumulating round-off error
		this.viewing_quaternion.normalize();
	
		// save x, y as last x, y
		this.last_x = x;
		this.last_y = y;
	}
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseEntered(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseExited(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseMoved(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Starts rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse press event object.
   */
  public void mousePressed(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.last_x = mouse.getX();
      this.last_y = mouse.getY();
      this.rotate_world = true;
    }
  }

  /**
   * Stops rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse release event object.
   */
  public void mouseReleased(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.rotate_world = false;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @param drawable
   *          {@inheritDoc}
   * @param x
   *          {@inheritDoc}
   * @param y
   *          {@inheritDoc}
   * @param width
   *          {@inheritDoc}
   * @param height
   *          {@inheritDoc}
   */
  public void reshape(final GLAutoDrawable drawable, final int x, final int y,
      final int width, final int height) {
    final GL2 gl = (GL2)drawable.getGL();

    // prevent division by zero by ensuring window has height 1 at least
    final int newHeight = Math.max(1, height);

    // compute the aspect ratio
    final double ratio = (double) width / newHeight;

    // reset the projection coordinate system before modifying it
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();

    // set the viewport to be the entire window
    gl.glViewport(0, 0, width, newHeight);

    // set the clipping volume
    this.glu.gluPerspective(25, ratio, 0.1, 100);

    // camera positioned at (0,0,6), look at point (0,0,0), up vector (0,1,0)
    this.glu.gluLookAt(0, 0, 12, 0, 0, 0, 0, 1, 0);

    // switch back to model coordinate system
    gl.glMatrixMode(GL2.GL_MODELVIEW);
  }

  private void toggleSelection(final Component component) {
    if (this.selectedComponents.contains(component)) {
      this.selectedComponents.remove(component);
      component.setColor(INACTIVE_COLOR);
    } else {
      this.selectedComponents.add(component);
      component.setColor(ACTIVE_COLOR);
    }
    this.stateChanged = true;
  }

  private void toggleSelection(final Leg leg) {
	    if (this.selectedLegs.contains(leg)) {
	      this.selectedLegs.remove(leg);
	      this.selectedComponents.removeAll(leg.joints());
	      for (final Component joint : leg.joints()) {
	        joint.setColor(INACTIVE_COLOR);
	      }
	    } else {
	      this.selectedLegs.add(leg);
	    }
	    this.stateChanged = true;
	  }
  
@Override
public void dispose(GLAutoDrawable drawable) {
	// TODO Auto-generated method stub	
}
}
