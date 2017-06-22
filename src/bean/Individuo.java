
package bean;

import ij.ImagePlus;
import ij.process.ImageProcessor;

public class Individuo {
    private int pixelsIguais;
    private ImagePlus img;
    private ImageProcessor ip;
    private boolean pixelIgual[][];
    
    public Individuo(){
        this.pixelsIguais = 0;
    }
    
    public Individuo(ImagePlus img) {
        this.img = img;
        ip = img.getProcessor();
        pixelIgual = new boolean[ip.getWidth()][ip.getHeight()];
        this.pixelsIguais = 0;
    }

    public boolean getPixelIgual(int largura, int altura) {
        return pixelIgual[largura][altura];
    }

    public void setPixelIgual(int largura, int altura, boolean verificacao) {
        this.pixelIgual[largura][altura]=verificacao;
    }

    public int getPixelsIguais() {
        return pixelsIguais;
    }

    public void setPixelsIguais(int pixelsIguais) {
        this.pixelsIguais = pixelsIguais;
    }

    public ImagePlus getImg() {
        return img;
    }

    public void setImg(ImagePlus img) {
        this.img = img;
    }

    public ImageProcessor getIp() {
        return ip;
    }

    public void setIp(ImageProcessor ip) {
        this.ip = ip;
    }

    
}
