package com.vp.plugin.sample.reloadclasses.actions;

import java.awt.*;
import java.awt.geom.*;

import com.vp.plugin.diagram.*;

public class ReloadClassesShapeController implements VPShapeController {

	@Override
	public void drawShape(Graphics2D aG, Paint aLineColor, Paint aFillColor, Stroke aStroke, VPShapeInfo aShapeInfo) {
		
		Shape lShape = createShape(aShapeInfo);
		aG.setColor(new Color(255, 211, 15));
		aG.fill(lShape);
		aG.setColor(Color.black);
		aG.draw(lShape);
	}

	@Override
	public boolean contains(int aX, int aY, VPShapeInfo aShapeInfo) {
		Shape lShape = createShape(aShapeInfo);
		return lShape.contains(aX, aY);
	}
	
	@SuppressWarnings("unused")
	private Shape createShape(VPShapeInfo aShapeInfo) {
		Rectangle2D lBounds = aShapeInfo.getBounds();
		
		double lWidth = lBounds.getWidth();
		double lHeight = lBounds.getHeight();
		
		if (true) { // <---- change this to FALSE to draw another shape
			// kind 1
			double lXUnit = lWidth/18;
			double lYUnit = lHeight/18;
			GeneralPath lPath = new GeneralPath();
			Point2D.Double[] lPoints = new Point2D.Double[] {
					new Point.Double(lXUnit*1, lYUnit*5), 
					new Point.Double(lXUnit*11, lYUnit*0), 
					new Point.Double(lXUnit*17, lYUnit*14), 
					new Point.Double(lXUnit*8, lYUnit*17), 
					new Point.Double(lXUnit*2, lYUnit*16)
			};
			
			int lIndex = 0;
			for (Point2D.Double lPoint : lPoints) {
				lPath.append(new Line2D.Double(lPoint, lPoints[(lIndex+1)%lPoints.length]), true);
				lIndex++;
			}
			
			lPath.append(new Arc2D.Double(lXUnit*3, lYUnit*6, lXUnit*3, lYUnit*4, 0, 360, Arc2D.OPEN), false);
			lPath.append(new Arc2D.Double(lXUnit*8, lYUnit*4.5, lXUnit*3, lYUnit*4, 0, 360, Arc2D.OPEN), false);
			
			lPath.append(new Line2D.Double(lXUnit*6.5, lYUnit*12.5, lXUnit*7.8, lYUnit*13), false);
			lPath.append(new Line2D.Double(lXUnit*7.8, lYUnit*13, lXUnit*9, lYUnit*12), false);
			
			return lPath;
		}
		else {
			// kind 2
			double lXUnit = lWidth/18;
			double lYUnit = lHeight/18;
			GeneralPath lPath = new GeneralPath();
			
			lPath.append(new Arc2D.Double(lXUnit*0, lYUnit*0, lXUnit*18, lYUnit*18, 0, 360, Arc2D.OPEN), false);
			
			lPath.append(new Arc2D.Double(lXUnit*3.5, lYUnit*6, lXUnit*3, lYUnit*5, 0, 180, Arc2D.OPEN), false);
			lPath.append(new Arc2D.Double(lXUnit*8.5, lYUnit*6, lXUnit*3, lYUnit*5, 0, 180, Arc2D.OPEN), false);
			
			lPath.append(new Arc2D.Double(lXUnit*6.5, lYUnit*12, lXUnit*3, lYUnit*3, 180, 180, Arc2D.OPEN), false);
			
			return lPath;
		}
		
//		return new Rectangle((int) lBounds.getX(), (int) lBounds.getY(), (int) lBounds.getWidth(), (int) lBounds.getHeight());
	}

}
