package imgMatching;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageMatching extends Thread {
	
//	static String filename1 = "D:\\img\\tree1.png";
//	static String filename2 = "D:\\img\\tree2.png";
//	static String filename3 = "D:\\img\\tree3.png";
	
	public static int ifCompareFeature(String filename1, String filename2) {
		int ret;
		ret = compareFeature(filename1, filename2);
		//ret = compareFeature(filename2, filename3);
		
		if (ret > 0) {
			System.out.println("Two images are same.");
			return 1;
		} else {
			System.out.println("Two images are different.");
			return 0;
		}
	}
	
	public static int compareFeature(String path1, String path2) {
		int resualVal = 0;
		long startTime = System.currentTimeMillis();
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		// 이미지 비교
		Mat img1 = Imgcodecs.imread(path1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat img2 = Imgcodecs.imread(path2, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		
		// 이미지 키 포인트 잡기 (선언)
		MatOfKeyPoint keypoint1 = new MatOfKeyPoint();
		MatOfKeyPoint keypoint2 = new MatOfKeyPoint();
		Mat descriptor1 = new Mat();
		Mat descriptor2 = new Mat();	// 기술어
		
		// OBR 키 포인트 디렉터 정의 뽑아내기
		FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		
		// key point 디렉트
		detector.detect(img1, keypoint1);
		detector.detect(img2, keypoint2);
		
		// Extract descriptors
		extractor.compute(img1, keypoint1, descriptor1);
		extractor.compute(img2, keypoint2, descriptor2);
		
		// descriptor matcher 정의
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
		
		// 이미지 매치포인트 잡기
		MatOfDMatch matches = new MatOfDMatch();
//		System.out.println("Type of Image1= " + descriptors1.type() + ", Type of Image2= " + descriptors2.type());
//		System.out.println("Cols of Image1= " + descriptors1.cols() + ", Cols of Image2= " + descriptors2.cols());
		
		if(descriptor2.cols() == descriptor1.cols()) {
			matcher.match(descriptor1, descriptor2, matches);
			
			// key points 와 matches 비교
			DMatch[] match = matches.toArray();
			double max_dist = 0;
			double min_dist = 100;
			
			for (int i = 0; i < descriptor1.rows(); i++) {
				double dist = match[i].distance;
				if(dist < min_dist) {
					min_dist = dist;
				}
				if(dist > max_dist) {
					max_dist = dist;
				}
			}
			System.out.println("max_dist = " + max_dist + ", min_dist = " + min_dist);
			
			// Extract good images ( distances 가 10 미만)
			for (int i = 0; i < descriptor1.rows(); i++) {
				if(match[i].distance <= 10) {
					resualVal++;
				}
			}
		}
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("estimatedTime : " + estimatedTime + "ms");
		
		return resualVal;
	}

}
