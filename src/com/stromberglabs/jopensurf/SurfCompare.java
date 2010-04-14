/*
This work was derived from Chris Evan's opensurf project and re-licensed as the
3 clause BSD license with permission of the original author. Thank you Chris! 

Copyright (c) 2010, Andrew Stromberg
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither Andrew Stromberg nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Andrew Stromberg BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.stromberglabs.jopensurf;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;


public class SurfCompare extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	private BufferedImage imageB;
	private float mImageAXScale = 0;
	private float mImageAYScale = 0;
	private float mImageBXScale = 0;
	private float mImageBYScale = 0;
	private int mImageAWidth = 0;
	private int mImageAHeight = 0;
	private int mImageBWidth = 0;
	private int mImageBHeight = 0;
	private Surf mSurfA;
	private Surf mSurfB;
	
	private java.util.List<SURFInterestPoint> mAMatchingPoints;
	private List<SURFInterestPoint> mBMatchingPoints;
	
    public SurfCompare(BufferedImage image,BufferedImage imageB){
        this.image = image;
    	this.imageB = imageB;
    	mSurfA = new Surf(image);
    	mSurfB = new Surf(imageB);

    	mImageAXScale = (float)Math.min(image.getWidth(),800)/(float)image.getWidth();
    	mImageAYScale = (float)Math.min(image.getHeight(),800 * (float)image.getHeight()/(float)image.getWidth())/(float)image.getHeight();
    	
    	mImageBXScale = (float)Math.min(imageB.getWidth(),800)/(float)imageB.getWidth();
    	mImageBYScale = (float)Math.min(imageB.getHeight(),800 * (float)imageB.getHeight()/(float)imageB.getWidth())/(float)imageB.getHeight();
    	
    	mImageAWidth = (int)((float)image.getWidth() * mImageAXScale);
    	mImageAHeight = (int)((float)image.getHeight() * mImageAYScale);
    	mImageBWidth = (int)((float)imageB.getWidth() * mImageBXScale);
    	mImageBHeight = (int)((float)image.getHeight() * mImageBYScale);
    	
    	mAMatchingPoints = mSurfA.getMatchingPoints(mSurfB,true);
    	mBMatchingPoints = mSurfB.getMatchingPoints(mSurfA,true);
    }

    /**
     * Drawing an image can allow for more
     * flexibility in processing/editing.
     */
    protected void paintComponent(Graphics g) {
        // Center image in this component.
        g.drawImage(image,0,0,mImageAWidth,mImageAHeight,this);
        g.drawImage(imageB,mImageAWidth,0,mImageBWidth,mImageBHeight,Color.WHITE,this);
        
        //if there is a surf descriptor, go ahead and draw the points
        if ( mSurfA != null && mSurfB != null ){
        	drawIpoints(g,mSurfA.getUprightInterestPoints(),mAMatchingPoints,0,mImageAXScale,mImageAYScale);
        	drawIpoints(g,mSurfB.getUprightInterestPoints(),mBMatchingPoints,mImageAWidth,mImageBXScale,mImageBYScale);
        }
    }
    
    private void drawIpoints(Graphics g,java.util.List<SURFInterestPoint> points,java.util.List<SURFInterestPoint> commonPoints,int offset,float xScale,float yScale){
    	Graphics2D g2d = (Graphics2D)g;
    	g2d.setColor(Color.RED);
    	for ( SURFInterestPoint point : points ){
    		int x = (int)(xScale * point.getX()) + offset;
    		int y = (int)(yScale * point.getY());
    		g2d.drawOval(x,y,8,8);
    	}
    	g2d.setColor(Color.GREEN);
    	for ( SURFInterestPoint point : commonPoints ){
    		int x = (int)(xScale * point.getX()) + offset;
    		int y = (int)(yScale * point.getY());
    		g2d.drawOval(x,y,8,8);
    	}
    }
    
    public void display(){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JScrollPane(this));
        f.setSize(mImageAWidth+mImageBWidth,Math.max(mImageAHeight,mImageBHeight));
        f.setLocation(0,0);
        f.setVisible(true);
    }
    
    public void matchesInfo(){
      java.util.List<SURFInterestPoint> pointsA = mSurfA.getMatchingPoints(mSurfB,true);
      java.util.List<SURFInterestPoint> pointsB = mSurfB.getMatchingPoints(mSurfA,true);
      System.out.println("There are: " + pointsA.size() + " matching points of " + mSurfA.getUprightInterestPoints().size());
      System.out.println("There are: " + pointsB.size() + " matching points of " + mSurfB.getUprightInterestPoints().size());
    }
    
    public static void main(String[] args) throws IOException {
        BufferedImage imageA = ImageIO.read(new File(args[0]));
        BufferedImage imageB = ImageIO.read(new File(args[1]));
//        System.out.println(imageA);
//        System.out.println(imageB);
        SurfCompare show = new SurfCompare(imageA,imageB);
        show.display();
        //show.matchesInfo();
    }
}
