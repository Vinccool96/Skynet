package modele.game;

import controleur.Controleur;
import modele.elements.hitbox.HitBox;
import modele.elements.hitbox.MotionPoint;
import modele.elements.hitbox.RotationParameters;
import modele.game.game_objects.Enemy;
import modele.math.Vector2D;
import utilitaires.MathUtilitaires;

import java.awt.*;

public class EnemySpawner implements Bias {

	public static final float SELF_ROTATION_BIAS = 0.3f;
	public static final float SELF_ROTATION_START_VELOCITY_BIAS = 0.3f;

	public static final float ORIGIN_ROTATION_BIAS = 0.05f;
	public static final float ORIGIN_ROTATION_START_VELOCITY_BIAS = 0.9f;

	public static final float START_VELOCITY_BIAS = 0.5f;

	public final static int MIN_HEIGHT = Controleur.PLAFOND + Enemy.ENEMY_DIM / 2;
	public final static int MAX_HEIGHT = Controleur.PLANCHER - Enemy.ENEMY_DIM / 2;

	public HitBox spawn(short size) {

		RotationParameters rotP = new RotationParameters();
		float startHeight = startHeight();

		HitBox hb = new HitBox(size, size, new MotionPoint(Controleur.EDGE + size, startHeight,
				new Vector2D(startVelocity(), 0), new Vector2D(startAcceleration(), 0), new RotationParameters()),
				startSelfRotationParameters());

		hb.setOrigin(startOrigin(hb));

		return hb;
	}

	public HitBox spawnTop(short size) {

		HitBox hb = new HitBox(size, size, new MotionPoint(Controleur.EDGE + size, MIN_HEIGHT,
				new Vector2D(startVelocity(), 0), new Vector2D(startAcceleration(), 0), new RotationParameters()),
				startSelfRotationParameters());

		return hb;
	}

	public HitBox spawnBottom(short size) {

		HitBox hb = new HitBox(size, size, new MotionPoint(Controleur.EDGE + size, MAX_HEIGHT,
				new Vector2D(startVelocity(), 0), new Vector2D(startAcceleration(), 0), new RotationParameters()),
				startSelfRotationParameters());

		return hb;
	}

	private float startHeight() {

		int startHeight = (int) MathUtilitaires.getRandomInRange(MIN_HEIGHT, MAX_HEIGHT);

		return startHeight;
	}

	private float startAcceleration() {
		return (-1) * randRange(0, MotionPoint.MAX_ACCELERATION);
	}

	private float startVelocity() {
		float vel = 0;

		if (testBias(START_VELOCITY_BIAS)) {
			vel = (-1) * randRange(0, 1);
		}
		return vel;
	}

	private MotionPoint startOrigin(HitBox hb) {

		MotionPoint o = new MotionPoint((float) hb.getCenterPoint().getX(), Controleur.MID_HEIGHT);
		o.setAccelerationX(hb.getCenterPoint().accelerationX());
		o.setVelocityX(hb.getCenterPoint().velocityX());

		return o;
	}

	private RotationParameters startSelfRotationParameters() {

		RotationParameters rParams = new RotationParameters();

		if (testBias(SELF_ROTATION_BIAS)) {

			float acc = randRange(RotationParameters.MIN_ANGULAR_ACCELERATION,
					RotationParameters.MAX_ANGULAR_ACCELERATION);

			if (testBias(SELF_ROTATION_START_VELOCITY_BIAS)) {
				float vel = randRange(RotationParameters.MIN_ANGULAR_VEL, RotationParameters.MAX_ANGULAR_VEL);

				rParams.setAngularVelocity(vel);
			}
			rParams.setAngularAcceleration(acc);
		}

		return rParams;
	}

	private RotationParameters startOriginRotationParameters() {

		RotationParameters rParams = new RotationParameters();
		float acc = 0;
		float vel = 0;

		if (testBias(ORIGIN_ROTATION_BIAS)) {

			acc = randRange(RotationParameters.MIN_ANGULAR_ACCELERATION, RotationParameters.MAX_ANGULAR_ACCELERATION);

			if (testBias(ORIGIN_ROTATION_START_VELOCITY_BIAS)) {
				vel = randRange(RotationParameters.MIN_ANGULAR_VEL, RotationParameters.MAX_ANGULAR_VEL);

				rParams.setAngularVelocity(vel);
			}
			rParams.setAngularAcceleration(acc);
		}

		// return new RotationParameters();
		return rParams;
	}

	private float randRange(float min, float max) {
		return MathUtilitaires.getRandomInRange(min, max);
	}
}
