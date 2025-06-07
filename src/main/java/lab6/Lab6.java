package lab6;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

public class Lab6 {
    public static void main(String[] args) throws IOException {
        Path sourceDir = Paths.get("/Users/rush_user/IdeaProjects/labs/src/main/resources/lab6/images");
        Path targetDir = Paths.get("/Users/rush_user/IdeaProjects/labs/src/main/resources/lab6/output");

        if (!Files.isDirectory(sourceDir)) {
            System.err.println("Source is not a directory: " + sourceDir);
            return;
        }
        Files.createDirectories(targetDir);

        try (Stream<Path> paths = Files.list(sourceDir)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> {
                        String name = p.getFileName().toString().toLowerCase();
                        return name.endsWith(".jpg")
                               || name.endsWith(".jpeg")
                               || name.endsWith(".png");
                    })
                    .parallel()
                    .forEach(path -> {
                        try {
                            BufferedImage img = ImageIO.read(path.toFile());
                            if (img == null) {
                                return;
                            }

                            int w = img.getWidth();
                            int h = img.getHeight();
                            BufferedImage rotated = new BufferedImage(
                                    h, w,
                                    img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType()
                            );
                            Graphics2D g2 = rotated.createGraphics();
                            g2.translate(h / 2.0, w / 2.0);
                            g2.rotate(Math.PI / 2);
                            g2.translate(-w / 2.0, -h / 2.0);
                            g2.drawImage(img, 0, 0, null);
                            g2.dispose();

                            String name = path.getFileName().toString();
                            String ext = name.substring(name.lastIndexOf('.') + 1);
                            Path outPath = targetDir.resolve(name);
                            ImageIO.write(rotated, ext, outPath.toFile());
                        } catch (IOException e) {
                            System.err.println("Failed to process " + path + ": " + e.getMessage());
                        }
                    });
        }

        System.out.println("All images rotated and saved to " + targetDir);
    }
}
