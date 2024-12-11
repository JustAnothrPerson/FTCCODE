package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp (name = "Abbey", group = "NapoleonicCodeV2")
public class RIBBIT extends LinearOpMode {

    public DcMotor motorforwardright = null;
    public DcMotor motorforwardleft = null;
    public DcMotor motorbackright = null;
    public DcMotor motorbackleft = null;
    public DcMotor Arm = null;
    public Servo wrist = null;
    public CRServo intake = null;
    // public DcMotor Slide = null;
    final double ARM_TICKS_PER_DEGREE = 28
            * 250047.0 / 4913.0
            * 100.0 / 20.0
            * 1 / 360.0;


    final double ARM_COLLAPSED_INTO_ROBOT = 0;
    final double ARM_SCORE_SAMPLE_IN_LOW = 160 * ARM_TICKS_PER_DEGREE;


    final double INTAKE_COLLECT = -1.0;
    final double INTAKE_OFF = 0.0;
    final double INTAKE_DEPOSIT = 1.0;


    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE;

    boolean Strafing = false;
    double armPosition = ARM_COLLAPSED_INTO_ROBOT;
    double armPositionFudgeFactor;

    @Override
    public void runOpMode() {


        double left;
        double right;
        double forward;
        double rotate;
        double max;

        motorforwardleft = hardwareMap.get(DcMotor.class, "motorfowardleft");
        motorforwardright = hardwareMap.get(DcMotor.class, "motorforwardright");
        motorbackright = hardwareMap.get(DcMotor.class, "motorbackright");
        motorbackleft = hardwareMap.get(DcMotor.class, "motorbackleft");
        Arm = hardwareMap.get(DcMotor.class, "Arm");
        intake = hardwareMap.get(CRServo.class, "intake");
        wrist = hardwareMap.get(Servo.class, "hand");
        //Slide = hardwareMap.get(DcMotor.class, "slide");

        motorforwardleft.setDirection(DcMotor.Direction.REVERSE);
        motorbackleft.setDirection(DcMotor.Direction.REVERSE);
        motorforwardright.setDirection(DcMotor.Direction.FORWARD);
        motorbackright.setDirection(DcMotor.Direction.FORWARD);

        motorforwardleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorforwardright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorbackleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorbackright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ((DcMotorEx) Arm).setCurrentAlert(5, CurrentUnit.AMPS);

        Arm.setTargetPosition(0);
        Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intake.setPower(INTAKE_OFF);
        wrist.setPosition(0);
        telemetry.addLine("Robot Ready.");
        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {

            forward = -gamepad1.left_stick_y;
            rotate = gamepad1.right_stick_x;


            left = forward + rotate;
            right = forward - rotate;

            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }
            if (!Strafing) {
                motorforwardleft.setPower(left);
                motorforwardright.setPower(right);
                motorbackleft.setPower(left);
                motorbackright.setPower(right);
            }
            if (gamepad2.a) {
                intake.setPower(INTAKE_COLLECT);
            } else if (gamepad2.y) {
                intake.setPower(INTAKE_DEPOSIT);
            } else if (gamepad2.x) {
                armPosition = -ARM_SCORE_SAMPLE_IN_LOW;
            } else if (gamepad2.b) {
                intake.setPower(INTAKE_OFF);
            } else if (gamepad2.dpad_left) {
                wrist.setPosition(wrist.getPosition() + 0.05);

            } else if (gamepad2.dpad_right) {

            wrist.setPosition(wrist.getPosition() - 0.05);

        }else if (gamepad2.right_bumper) {
                if (Arm.getTargetPosition() >= -5360) {
                    armPosition -= 40;
                }
            } else if (gamepad2.left_bumper) {
                if (Arm.getTargetPosition() <= 200) {
                    armPosition += 40;
                }
            }
            if (gamepad1.right_bumper) {
                Strafing = true;
                motorforwardleft.setPower(-0.5);
                motorforwardright.setPower(0.5);
                motorbackleft.setPower(0.5);
                motorbackright.setPower(-0.5);
            } else if (gamepad1.left_bumper) {
                Strafing = true;
                motorforwardleft.setPower(0.5);
                motorforwardright.setPower(-0.5);
                motorbackleft.setPower(-0.5);
                motorbackright.setPower(0.5);
            } else {
                Strafing = false;
            }


            armPositionFudgeFactor = FUDGE_FACTOR * (gamepad1.right_trigger + (-gamepad1.left_trigger));

            Arm.setTargetPosition((int) (armPosition + armPositionFudgeFactor));
            ((DcMotorEx) Arm).setVelocity(2100);
            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (((DcMotorEx) Arm).isOverCurrent()) {
                telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!!");
            }

            telemetry.addData("armTarget: ", Arm.getTargetPosition());
            telemetry.addData("arm Encoder:  ", Arm.getCurrentPosition());
            telemetry.addData("wrist position: ", wrist.getPosition());
            telemetry.addData("intake power  ", intake.getPower());
            telemetry.update();

        }
    }
}
