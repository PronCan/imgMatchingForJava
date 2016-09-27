import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

public class Main {
	
//	public void HandleSinglePixel(int x, int y, int pixel) {
//
//		int alpha = (pixel >> 24) & 0xff;	// 투명도
//		int red = (pixel >> 16) & 0xff;
//		int green = (pixel >> 8) & 0xff;
//		int blue = (pixel) & 0xff;
//	}
//	
//	public void HandlePixel(Image img, int x, int y, int width, int height) {
//		int[] pixels = new int[width * height];
//		PixelGrabber pg = new PixelGrabber(img, x, y, width, height, pixels, 0, width);
//		try {
//			pg.grabPixels();
//		} catch(InterruptedException e) {
//			System.err.println("interrupted waiting for pixels");
//			return;
//		}
//		
//		if((pg.getStatus() & ImageObserver.ABORT) != 0) {
//			System.err.println("image fetch aborted or errored");
//			return;
//		}
//		
//		for(int i=0; i<height; i++) {
//			for(int j=0; j<width; j++) {
//				HandleSinglePixel(x+j, y+i, pixels[i * width + j]);
//			}
//		}
//	}

	
	/////////////////////////////////////////////////////////////
	
	static BufferedImage newImage;
	private static Image resizeImage;
	
	public static void main(String[] args) {
		Image img = new ImageIcon("img/tree1.png").getImage();
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		
		int[] pixels = new int[width * height];
		
		newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int color = Color.DARK_GRAY.getRGB();
		
		for (int i = 0; i < pixels.length; i++) {
			if((pixels[i] & 0xff) == 0 || ((pixels[i] >> 16) & 0xff) == 0 || ((pixels[i] >> 8 ) & 0xff) == 0) {
				newImage.setRGB(i % width, i / width, color);
			}
		}
		
		Graphics g = newImage.getGraphics();
		try {
			ImageIO.write(newImage, "png", newFile("D:/새 사진파일명.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static ImageOutputStream newFile(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}