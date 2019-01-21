package hangman;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public class AsciiPictureTest {
	
	char[][] hangman = { 
		"  _________     ".toCharArray(),
		" |         |    ".toCharArray(),
		" |         0    ".toCharArray(),
		" |        /|\\   ".toCharArray(),
		" |        / \\   ".toCharArray(),
		" |              ".toCharArray(),
		" |              ".toCharArray() 
	};

	@Test
	public void testAsciiPictureEmpty(){
		AsciiPicture ap = new AsciiPicture(5, 5, '*');
		
		char[][] stars = new char[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				stars[i][j] = '*';
			}
		}
		assertArrayEquals(stars, ap.picture);
	}

	@Test
	public void testGet(){
		AsciiPicture ap = new AsciiPicture(5, 5, '*');
		assertEquals('*', ap.get(0, 4));
	}
	
	@Test
	public void testSet(){
		AsciiPicture ap = new AsciiPicture(5, 5, '*');

		char[][] starsEd = new char[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				starsEd[i][j] = '*';
			}
		}
		ap.set(2, 3, '%');
		starsEd[3][2] = '%';
		assertArrayEquals(starsEd, ap.picture);
	}
	
	@Test
	public void testAsciiPicturePremade(){
		AsciiPicture ap = new AsciiPicture(16, 7, 0, 0, hangman);
		assertArrayEquals(hangman, ap.picture);
	}
	
	@Test
	public void testOverlay(){
		char[][] boobs = {
				"   *   ".toCharArray(),
				" (_|_) ".toCharArray(),
				"   |   ".toCharArray(),
				"  / \\  ".toCharArray()
		};	
	AsciiPicture boobsPic = new AsciiPicture(7, 4, 0, 0, boobs);
	AsciiPicture ap = new AsciiPicture(16, 7, 0, 0, hangman);
	ap.overlay(boobsPic, 8, 2, ' ');

		char[][] finalPic = { 
				"  _________     ".toCharArray(),
				" |         |    ".toCharArray(),
				" |         *    ".toCharArray(),
				" |       (_|_)  ".toCharArray(),
				" |        /|\\   ".toCharArray(),
				" |        / \\   ".toCharArray(),
				" |              ".toCharArray() 
			};
		assertArrayEquals(finalPic, ap.picture);
	}
	
	@Test
	public void testPrint() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(out);
	    String sTest = "";
	    AsciiPicture ap = new AsciiPicture(16, 7, 0, 0, hangman);
		
	    ap.print(ps);	
	    for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 16; j++) {
				sTest += ap.picture[i][j];
			}
			sTest += "\n";
		}
	    for (int i = 0; i < ap.picture.length; i++) {
			assertEquals(out.toString().toCharArray()[i], 
			sTest.toCharArray()[i]);	
		}
	}
	
	@Test
	public void testOverlayX(){
		AsciiPicture ap = new AsciiPicture(4, 4, '*');
		AsciiPicture over = new AsciiPicture(5, 5, '%');
		AsciiPicture expect = new AsciiPicture(4, 4, '%');
		
		ap.overlay(over, 0, 0, 'o');
		assertArrayEquals(expect.picture, ap.picture);
	}
	
	@Test
	public void testOverlayMin(){
		AsciiPicture ap = new AsciiPicture(4, 4, '*');
		AsciiPicture over = new AsciiPicture(5, 5, '%');
		
		char[][] expect = {
				"%%%*".toCharArray(),
				"%%%*".toCharArray(),
				"%%%*".toCharArray(),
				"****".toCharArray()
		};
		
		ap.overlay(over, -2, -2, 'o');
		assertArrayEquals(expect, ap.picture);
	}
}
