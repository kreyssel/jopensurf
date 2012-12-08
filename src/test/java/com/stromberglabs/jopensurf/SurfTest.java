package com.stromberglabs.jopensurf;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Ignore;
import org.junit.Test;

public class SurfTest {
	/**
	 * This is a simple function that just tests that the high level output from
	 * lenna.png . I am using this to test refactoring so that I can insure that
	 * the output of the algorithm didn't change
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore("test in and out is not identical")
	public void testSURFOutput() throws Exception {
		Surf original = Surf.readFromFile("src/test/resources/lenna_surf_test.bin");
		Surf current = new Surf(ImageIO.read(new File("src/test/resources/lenna.png")));
		Surf.saveToFile(current, "target/lenna_surf_test.bin");
		assertTrue(original.isEquivalentTo(current));
	}
}
