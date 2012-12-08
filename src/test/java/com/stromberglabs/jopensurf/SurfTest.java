package com.stromberglabs.jopensurf;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Test;


public class SurfTest {
	/**
	 * This is a simple function that just tests that the high level output
	 * from lenna.png . I am using this to test refactoring so that I can insure
	 * that the output of the algorithm didn't change 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSURFOutput() throws Exception {
		Surf original = Surf.readFromFile("H:\\workspace\\javaopensurf\\example\\lenna_surf_test.bin");
		Surf current = new Surf(ImageIO.read(new File("H:\\workspace\\javaopensurf\\example\\lenna.png")));
		//Surf.saveToFile(current,"H:\\workspace\\javaopensurf\\example\\lenna_surf_test.bin");
		assertTrue(original.isEquivalentTo(current));
	}
}
