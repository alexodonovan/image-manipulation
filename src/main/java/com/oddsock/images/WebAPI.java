package com.oddsock.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class WebAPI {

	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping(produces = { MediaType.IMAGE_JPEG_VALUE })
	public byte[] hello() throws IOException {
		BufferedImage cat = loadImage("cat.jpg");
		BufferedImage minions = loadImage("minions.jpg");

		BufferedImage concat = new BufferedImage(minions.getWidth(), minions.getHeight() + cat.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = concat.createGraphics();
		g2d.drawImage(cat, 0, 0, cat.getWidth(), cat.getHeight(), null);
		g2d.drawImage(minions, 0, cat.getHeight(), minions.getWidth(), minions.getHeight(), null);
		g2d.dispose();

		ByteArrayOutputStream concatOS = new ByteArrayOutputStream();
		ImageIO.write(concat, "jpg", concatOS);

		return concatOS.toByteArray();
	}

	private BufferedImage loadImage(String filename) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + filename);
		return ImageIO.read(resource.getFile());
	}

}
