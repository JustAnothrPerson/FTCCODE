package org.firstinspires.ftc.teamcode.NapoleanV2;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "SecretServo", group = "NapoleonicCodeV2")
public class ArizonaTea extends LinearOpMode {

    public DcMotor motorforwardright = null;
    public DcMotor motorforwardleft = null;
    public DcMotor motorbackright = null;
    public DcMotor motorbackleft = null;
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {


        motorforwardleft = hardwareMap.get(DcMotor.class, "motorfowardleft");
        motorforwardright = hardwareMap.get(DcMotor.class, "motorforwardright");
        motorbackright = hardwareMap.get(DcMotor.class, "motorbackright");
        motorbackleft = hardwareMap.get(DcMotor.class, "motorbackleft");


        motorforwardleft.setDirection(DcMotor.Direction.REVERSE);
        motorbackleft.setDirection(DcMotor.Direction.REVERSE);
        motorforwardright.setDirection(DcMotor.Direction.FORWARD);
        motorbackright.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "ready to run");
         telemetry.update();

        waitForStart();


        motorforwardright.setPower(0.5);
        motorforwardleft.setPower(-0.5);
        motorbackleft.setPower(0.5);
        motorbackright.setPower(-0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        motorforwardright.setPower(-0.5);
        motorforwardleft.setPower(0.5);
        motorbackleft.setPower(-0.5);
        motorbackright.setPower(0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.25)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //20 inches a second
        motorforwardright.setPower(0.5);
        motorforwardleft.setPower(0.5);
        motorbackleft.setPower(0.5);
        motorbackright.setPower(0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() <= 2)) {

            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
}
/*
        motorforwardright.setPower(-0.5);
        motorforwardleft.setPower(0.5);
        motorbackleft.setPower(-0.5);
        motorbackright.setPower(0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() <= 4.1)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
            motorforwardleft.setPower(0);
            motorbackright.setPower(0);
            motorforwardright.setPower(0);
            motorbackleft.setPower(0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);




    }
}*/