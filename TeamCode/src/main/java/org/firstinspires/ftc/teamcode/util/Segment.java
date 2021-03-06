package org.firstinspires.ftc.teamcode.util;


import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.ShelbyBot;

import java.util.Locale;

public class Segment
{
    public Segment(String name, Point2d start, Point2d end)
    {
        this.name   = name;
        this.strtPt = start;
        this.tgtPt  = end;
        this.fldHdg = angle();
        len = start.distance(end);
        this.dir = ShelbyBot.DriveDir.SWEEPER;
        this.speed = DEF_SEG_SPD;
        this.act = Action.NOTHING;
        this.drvTuner = 1.0;
        this.tgtType = TargetType.ENCODER;
    }

    public ShelbyBot.DriveDir getDir()
    {
        return dir;
    }
    public double getFieldHeading()
    {
        return fldHdg;
    }

    public String getName()
    {
        return name;
    }
    public Point2d getStrtPt()
    {
        return strtPt;
    }
    public Point2d getTgtPt()
    {
        return tgtPt;
    }
    public double getSpeed() { return speed; }
    public Action getAction() { return act; }
    public double getLength() { return len; }
    public double getDrvTuner() { return drvTuner; }
    public Double getPostTurn() { return postTurn; }
    public TargetType getTgtType() { return tgtType; }

    public void setAction(Action act) { this.act = act; }
    public void setDir(ShelbyBot.DriveDir dir)
    {
        this.dir = dir;
    }
    public void setSpeed(double spd) { this.speed = spd; }
    public void setDrvTuner(double drvTuner) { this.drvTuner = drvTuner; }
    public void setPostTurn(double postTurn) { this.postTurn = Double.valueOf(postTurn); }
    public void setStrtPt(Point2d spt)
    {
        this.strtPt = spt;
        this.fldHdg = angle();
        len = strtPt.distance(tgtPt);
    }
    public void setEndPt(Point2d ept)
    {
        this.tgtPt = ept;
        this.fldHdg = angle();
        len = strtPt.distance(tgtPt);
    }
    public void setTgtType(TargetType tgtType)
    {
        this.tgtType = tgtType;
    }

    public double angle()
    {
        double tgtFldHdg = Math.atan2(tgtPt.getY() - strtPt.getY(), (tgtPt.getX() - strtPt.getX()));
        return Math.toDegrees(tgtFldHdg);
    }

    public String toString()
    {
        double pturn = 0.0;
        if(postTurn != null) pturn = postTurn;

        return String.format(Locale.US, "%s: %s - %s len: %5.2f %s hdg: %5.2f " +
                                        " spd: %.3f act: %s post: %5.2f",
                name, strtPt, tgtPt, len, dir.toString(), fldHdg, speed, act.toString(), pturn);
    }

    //enum SegDir {FORWARD, REVERSE}
    public enum Action {NOTHING, SHOOT, SCAN_IMAGE, FIND_BEACON, PUSH, RST_PUSHER}
    public enum TargetType{ENCODER, TIME, COLOR}
    private static final double DEF_SEG_SPD = 0.5;
    private double  fldHdg = 0.0;
    private Point2d strtPt;
    private Point2d tgtPt;
    private String  name;
    private ShelbyBot.DriveDir dir;
    private double speed;
    private double len = 0;
    private Action act = Action.NOTHING;
    private double drvTuner = 1.0;
    private Double postTurn = null;
    private TargetType tgtType = TargetType.ENCODER;

    public static void main(String[] args)
    {
//        Point2d ptA = new Point2d(-12.0, -61.0);
//        Point2d ptB = new Point2d(-50.0, -18.0);
//        Segment seg = new Segment("SEG", ptA, ptB);
//        System.out.print("FLDHDG:" + seg.getFieldHeading() + "\n");
        class MyRunnable implements Runnable
        {
            private int myVal = 0;
            private int oldMyVal = -1;
            ElapsedTime timer = new ElapsedTime();

            public void setMyVal(int val) {myVal = val;}

            public void run()
            {
                while(timer.seconds() < 30)
                {
                    if(myVal != oldMyVal)
                    {
                        System.out.println(Thread.currentThread().getId() + " New value " + myVal);
                        oldMyVal = myVal;
                    }
                    try
                    {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException ie)
                    {
                        System.out.println("Interrupted");
                    }
                }
            }
        }

        MyRunnable mr = new MyRunnable();
        Thread myThread = new Thread(mr);
        myThread.start();

        System.out.println("Main " + Thread.currentThread().getId());
        for (int x = 1; x < 10; x++)
        {
            mr.setMyVal(x);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ie)
            {
                System.out.println("Interrupted");
            }
        }
    }
}
