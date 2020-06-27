/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;

/**
 *
 * @author Eduardo
 */
public class Filtros {

    public BufferedImage pretoeBranco(BufferedImage original, int contraste) {

        int w, h;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[h * w * 3];
        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);

        /*
         Calculo para achar o valor do contraste de 0 a 100
         255 = 100
          ?  = constraste
         
         limiar = 255*contraste/100
         */
        int limiar = 255 * contraste / 100;

        for (int i = 0; i < pixels.length; i += 3) {
            int ml = (pixels[i] + pixels[i + 1] + pixels[i + 2]) / 3;
            if (ml < limiar) {
                pixels[i] = 0;
                pixels[i + 1] = 0;
                pixels[i + 2] = 0;

            } else {
                pixels[i] = 255;
                pixels[i + 1] = 255;
                pixels[i + 2] = 255;
            }

        }

        raster.setPixels(0, 0, w, h, pixels);

        return processada;
    }

    public BufferedImage cinza(BufferedImage original) {
        int w, h;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[h * w * 3];
        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);

        for (int i = 0; i < pixels.length; i += 3) {
            int ml = (int) ((pixels[i] * 0.3) + (pixels[i + 1] * 0.59) + (pixels[i + 2] * 0.11));

            pixels[i] = ml;
            pixels[i + 1] = ml;
            pixels[i + 2] = ml;

        }

        raster.setPixels(0, 0, w, h, pixels);

        return processada;
    }

    //CRIA IMAGEM COM LISTRAS COM TOM DE CINZA
    public BufferedImage CinzaZebrado(BufferedImage original, int faixas) {
        int w, h;

        w = original.getWidth();
        h = original.getHeight();

        WritableRaster raster = original.getRaster();

        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        int qtpixelsfaixas = w / faixas;
        int[] pixels = new int[h * w * 3];
        int qtpixelsmudado = 0;
        faixas = 0;

        for (int i = 1; i < w - 1; i++) {

            if (qtpixelsmudado >= qtpixelsfaixas) {
                qtpixelsmudado = 0;
                faixas++;
            }
            for (int j = 1; j < original.getHeight() - 1; j++) {
                if (faixas == 0 || faixas % 2 == 0) {
                    raster.getPixel(i, j, pixels);
                    double media = (pixels[0] * 0.3 + pixels[1] * 0.59 + pixels[2] * 0.11) / 3;
                    pixels[0] = (int) media;
                    pixels[1] = (int) media;
                    pixels[2] = (int) media;
                    raster.setPixel(i, j, pixels);
                }
            }
            qtpixelsmudado++;
        }
        try {
            processada.setData(raster);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processada;
    }

    public BufferedImage sepia(BufferedImage original) {
        int w, h;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[h * w * 3];
        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);

        for (int i = 0; i < pixels.length; i += 3) {
            int tr, tg, tb, r, g, b;

            r = pixels[i];
            g = pixels[i + 1];
            b = pixels[i + 2];

            tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

            if (tr > 255) {
                pixels[i] = 255;
            } else {
                pixels[i] = tr;
            }

            if (tg > 255) {
                pixels[i + 1] = 255;
            } else {
                pixels[i + 1] = tg;
            }

            if (tb > 255) {
                pixels[i + 2] = 255;
            } else {
                pixels[i + 2] = tb;
            }

        }

        raster.setPixels(0, 0, w, h, pixels);

        return processada;
    }

    public BufferedImage espelho(BufferedImage original) {

        int altImg, largImg;

        altImg = original.getHeight();
        largImg = original.getWidth();

        BufferedImage processada = new BufferedImage(largImg, altImg, BufferedImage.TYPE_INT_RGB);

        // int[] pixels=new int[altImg*largImg*3];
        WritableRaster rasterProcessada = processada.getRaster();
        WritableRaster rasterOriginal = original.getRaster();
        // imgOriginal.getRaster().getPixels(0, 0, largImg, altImg, pixels);
        int[] pixelIni = new int[3];
        int[] pixelFim = new int[3];
        int ultiPixel = 0;
        int inip = 0;
        for (int y = 0; y < altImg; y++) {
            for (inip = 0; inip < (largImg / 2); inip++) {
                //ultiPixel=largImg;
                ultiPixel = ((largImg - inip) - 1);

                rasterOriginal.getPixel(inip, y, pixelIni);
                rasterOriginal.getPixel(ultiPixel, y, pixelFim);

                rasterProcessada.setPixel(inip, y, pixelFim);
                rasterProcessada.setPixel(ultiPixel, y, pixelIni);
            }
            if ((largImg % 2) != 0) {
                rasterOriginal.getPixel(inip, y, pixelIni);
                rasterOriginal.getPixel(ultiPixel, y, pixelFim);

                rasterProcessada.setPixel(inip, y, pixelFim);
                rasterProcessada.setPixel(ultiPixel, y, pixelIni);
            }

        }
        //raster.setPixels(0, 0, largImg, altImg, pixels);

        return processada;

    }

    public BufferedImage negativo(BufferedImage original) {
        int altImg, largImg;

        altImg = original.getHeight();
        largImg = original.getWidth();

        BufferedImage processada = new BufferedImage(largImg, altImg, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[altImg * largImg * 3];
        // int[] pixels=new int[altImg*largImg*3];
        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, largImg, altImg, pixels);

        for (int i = 0; i < pixels.length; i += 3) {
            int r = pixels[i];
            int g = pixels[i + 1];
            int b = pixels[i + 2];
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;
            pixels[i] = r;
            pixels[i + 1] = g;
            pixels[i + 2] = b;
        }
        raster.setPixels(0, 0, largImg, altImg, pixels);
        return processada;
    }

    public BufferedImage linhaViraColuna(BufferedImage original) {
        int w, h;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);

        int[] pixels = new int[h * w * 3];
        int[] pixels2 = new int[h * w * 3];

        int horizontal = pixels.length / w;
        int vertical = pixels.length / h / 3;
        int y = 1;
        int cont = 0;

        WritableRaster raster = processada.getRaster();

        original.getRaster().getPixels(0, 0, w, h, pixels);

        for (int i = 0; i < pixels.length; i += 3) {

            pixels2[pixels.length - (horizontal * y) + cont] = pixels[i];
            pixels2[pixels.length - (horizontal * y) + cont + 1] = pixels[i + 1];
            pixels2[pixels.length - (horizontal * y) + cont + 2] = pixels[i + 2];

            y++;

            if (y > vertical) {
                y = 1;
                cont += 3;
            }
        }

        raster.setPixels(0, 0, h, w, pixels2);

        return processada;
    }

    public BufferedImage colunaViraLinha(BufferedImage original) {
        int w, h;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);

        int[] pixels = new int[h * w * 3];
        int[] pixels2 = new int[h * w * 3];

        int horizontal = pixels.length / w;
        int vertical = pixels.length / h / 3;
        int y = 0;
        int cont = (h * 3) - 3;

        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);

        for (int i = 0; i < pixels.length; i += 3) {

            pixels2[cont + (horizontal * y)] = pixels[i];
            pixels2[cont + (horizontal * y) + 1] = pixels[i + 1];
            pixels2[cont + (horizontal * y) + 2] = pixels[i + 2];

            y++;

            if (y % vertical == 0) {
                y = 0;
                cont -= 3;
            }
        }

        raster.setPixels(0, 0, h, w, pixels2);

        return processada;
    }

    public BufferedImage escolherTons(BufferedImage original, int r, int g, int b) {
        int w, h;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[h * w * 3];
        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);

        for (int i = 0; i < pixels.length; i += 3) {

            pixels[i] = (int) (pixels[i] * (r / 100.0));
            pixels[i + 1] = (int) (pixels[i + 1] * (g / 100.0));
            pixels[i + 2] = (int) (pixels[i + 2] * (b / 100.0));

        }

        raster.setPixels(0, 0, w, h, pixels);

        return processada;
    }

    public BufferedImage media(BufferedImage original) {

        BufferedImage processada = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        Raster raster = original.getRaster();
        WritableRaster wraster = processada.getRaster();

        int r, g, b, w, h;

        w = original.getWidth();
        h = original.getHeight();

        double valornr, valorng, valornb;
        double p[][] = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = 1.0 / 9.0;
            }
        }

        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {

                r = raster.getSample(x, y, 0);
                g = raster.getSample(x, y, 1);
                b = raster.getSample(x, y, 2);

                valornr = p[0][0] * (double) raster.getSample(x - 1, y - 1, 0) + p[0][1] * (double) raster.getSample(x, y - 1, 0)
                        + p[0][2] * (double) raster.getSample(x + 1, y - 1, 0)
                        + p[1][0] * (double) raster.getSample(x - 1, y, 0) + p[1][1] * (double) raster.getSample(x, y, 0)
                        + p[1][2] * (double) raster.getSample(x + 1, y, 0)
                        + p[2][0] * (double) raster.getSample(x - 1, y + 1, 0) + p[2][1] * (double) raster.getSample(x, y + 1, 0)
                        + p[2][2] * (double) raster.getSample(x + 1, y + 1, 0);

                valorng = p[0][0] * (double) raster.getSample(x - 1, y - 1, 1) + p[0][1] * (double) raster.getSample(x, y - 1, 1)
                        + p[0][2] * (double) raster.getSample(x + 1, y - 1, 1)
                        + p[1][0] * (double) raster.getSample(x - 1, y, 1) + p[1][1] * (double) raster.getSample(x, y, 1)
                        + p[1][2] * (double) raster.getSample(x + 1, y, 1)
                        + p[2][0] * (double) raster.getSample(x - 1, y + 1, 1) + p[2][1] * (double) raster.getSample(x, y + 1, 1)
                        + p[2][2] * (double) raster.getSample(x + 1, y + 1, 1);

                valornb = p[0][0] * (double) raster.getSample(x - 1, y - 1, 2) + p[0][1] * (double) raster.getSample(x, y - 1, 2)
                        + p[0][2] * (double) raster.getSample(x + 1, y - 1, 2)
                        + p[1][0] * (double) raster.getSample(x - 1, y, 2) + p[1][1] * (double) raster.getSample(x, y, 2)
                        + p[1][2] * (double) raster.getSample(x + 1, y, 2)
                        + p[2][0] * (double) raster.getSample(x - 1, y + 1, 2) + p[2][1] * (double) raster.getSample(x, y + 1, 2)
                        + p[2][2] * (double) raster.getSample(x + 1, y + 1, 2);

                wraster.setSample(x, y, 0, (int) (valornr + 0.5));
                wraster.setSample(x, y, 1, (int) (valorng + 0.5));
                wraster.setSample(x, y, 2, (int) (valornb + 0.5));
            }
        }

        return processada;
    }

    public BufferedImage moda(BufferedImage original) {

        int w, h;
        int mr = 0, mg = 0, mb = 0, cc = 0;

        w = original.getWidth();
        h = original.getHeight();
        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        int[] pixels = new int[h * w * 3];

        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);
        WritableRaster ro = original.getRaster();

        for (int x = 1; x < w - 1; x++) {
            for (int y = 1; y < h - 1; y++) {
                mr = 0;
                mb = 0;
                mg = 0;
                int[][] resultado = new int[9][3];
                int[] rgb = new int[3];
                int[] rr = new int[9];
                int[] rg = new int[9];
                int[] rb = new int[9];

                ro.getPixel(x - 1, y - 1, resultado[0]);
                ro.getPixel(x, y - 1, resultado[1]);
                ro.getPixel(x + 1, y - 1, resultado[2]);
                ro.getPixel(x - 1, y, resultado[3]);
                ro.getPixel(x, y, resultado[4]);
                ro.getPixel(x + 1, y, resultado[5]);
                ro.getPixel(x - 1, y + 1, resultado[6]);
                ro.getPixel(x, y + 1, resultado[7]);
                ro.getPixel(x + 1, y + 1, resultado[8]);

                int auxcc;
                cc = 0;
                for (int qt = 0; qt < resultado.length; qt++) {
                    auxcc = 0;
                    for (int ct = qt + 1; ct < resultado.length; ct++) {
                        if (resultado[ct][0] + resultado[ct][1] + resultado[ct][2] == resultado[qt][0] + resultado[qt][1] + resultado[qt][2]) {
                            auxcc++;
                        }
                    }
                    if (auxcc >= cc) {
                        if ((resultado[qt][0] + resultado[qt][1] + resultado[qt][2] > mr && auxcc == cc) || (auxcc > cc)) {
                            mr = resultado[qt][0];
                            mg = resultado[qt][1];
                            mb = resultado[qt][2];
                            cc = auxcc;
                        }
                    }

                }

                rgb[0] = mr;
                rgb[1] = mg;
                rgb[2] = mb;

                raster.setPixel(x, y, rgb);
            }
        }

        return processada;
    }

    public BufferedImage maxima(BufferedImage original) {
        int w, h, r, g, b;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        WritableRaster raster = processada.getRaster();
        WritableRaster ro = original.getRaster();

        for (int x = 1; x < w - 1; x++) {
            for (int y = 1; y < h - 1; y++) {
                int[][] resultado = new int[9][3];
                int[] rgb = new int[3];
                int[] rr = new int[9];
                int[] rg = new int[9];
                int[] rb = new int[9];

                ro.getPixel(x - 1, y - 1, resultado[0]);
                ro.getPixel(x, y - 1, resultado[1]);
                ro.getPixel(x + 1, y - 1, resultado[2]);
                ro.getPixel(x - 1, y, resultado[3]);
                ro.getPixel(x, y, resultado[4]);
                ro.getPixel(x + 1, y, resultado[5]);
                ro.getPixel(x - 1, y + 1, resultado[6]);
                ro.getPixel(x, y + 1, resultado[7]);
                ro.getPixel(x + 1, y + 1, resultado[8]);

                rr[0] = resultado[0][0];
                rr[1] = resultado[1][0];
                rr[2] = resultado[2][0];
                rr[3] = resultado[3][0];
                rr[4] = resultado[4][0];
                rr[5] = resultado[5][0];
                rr[6] = resultado[6][0];
                rr[7] = resultado[7][0];
                rr[8] = resultado[8][0];
                Arrays.sort(rr);

                rg[0] = resultado[0][1];
                rg[1] = resultado[1][1];
                rg[2] = resultado[2][1];
                rg[3] = resultado[3][1];
                rg[4] = resultado[4][1];
                rg[5] = resultado[5][1];
                rg[6] = resultado[6][1];
                rg[7] = resultado[7][1];
                rg[8] = resultado[8][1];
                Arrays.sort(rg);

                rb[0] = resultado[0][2];
                rb[1] = resultado[1][2];
                rb[2] = resultado[2][2];
                rb[3] = resultado[3][2];
                rb[4] = resultado[4][2];
                rb[5] = resultado[5][2];
                rb[6] = resultado[6][2];
                rb[7] = resultado[7][2];
                rb[8] = resultado[8][2];
                Arrays.sort(rb);

                rgb[0] = rr[8];
                rgb[1] = rg[8];
                rgb[2] = rb[8];

                raster.setPixel(x, y, rgb);
            }
        }

        return processada;
    }

    public BufferedImage minima(BufferedImage original) {
        int w, h, r, g, b;

        w = original.getWidth();
        h = original.getHeight();

        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        WritableRaster raster = processada.getRaster();
        WritableRaster ro = original.getRaster();

        for (int x = 1; x < w - 1; x++) {
            for (int y = 1; y < h - 1; y++) {
                int[][] resultado = new int[9][3];
                int[] rgb = new int[3];
                int[] rr = new int[9];
                int[] rg = new int[9];
                int[] rb = new int[9];

                ro.getPixel(x - 1, y - 1, resultado[0]);
                ro.getPixel(x, y - 1, resultado[1]);
                ro.getPixel(x + 1, y - 1, resultado[2]);
                ro.getPixel(x - 1, y, resultado[3]);
                ro.getPixel(x, y, resultado[4]);
                ro.getPixel(x + 1, y, resultado[5]);
                ro.getPixel(x - 1, y + 1, resultado[6]);
                ro.getPixel(x, y + 1, resultado[7]);
                ro.getPixel(x + 1, y + 1, resultado[8]);

                rr[0] = resultado[0][0];
                rr[1] = resultado[1][0];
                rr[2] = resultado[2][0];
                rr[3] = resultado[3][0];
                rr[4] = resultado[4][0];
                rr[5] = resultado[5][0];
                rr[6] = resultado[6][0];
                rr[7] = resultado[7][0];
                rr[8] = resultado[8][0];
                Arrays.sort(rr);

                rg[0] = resultado[0][1];
                rg[1] = resultado[1][1];
                rg[2] = resultado[2][1];
                rg[3] = resultado[3][1];
                rg[4] = resultado[4][1];
                rg[5] = resultado[5][1];
                rg[6] = resultado[6][1];
                rg[7] = resultado[7][1];
                rg[8] = resultado[8][1];
                Arrays.sort(rg);

                rb[0] = resultado[0][2];
                rb[1] = resultado[1][2];
                rb[2] = resultado[2][2];
                rb[3] = resultado[3][2];
                rb[4] = resultado[4][2];
                rb[5] = resultado[5][2];
                rb[6] = resultado[6][2];
                rb[7] = resultado[7][2];
                rb[8] = resultado[8][2];
                Arrays.sort(rb);

                rgb[0] = rr[0];
                rgb[1] = rg[0];
                rgb[2] = rb[0];

                raster.setPixel(x, y, rgb);
            }
        }

        return processada;
    }

    public BufferedImage zoomIn(BufferedImage original, int zoom) {

        BufferedImage processada = new BufferedImage(original.getWidth() * zoom, original.getHeight() * zoom, BufferedImage.TYPE_INT_RGB);
        //joga a imagem processada para dentro da variavel processada
        int h = original.getHeight();
        int w = original.getWidth();

        int[] pixels = new int[h * w * 3];

        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);
        WritableRaster ro = original.getRaster();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int[] rgb = new int[3];
                int xAmp = x * zoom;
                int yAmp = y * zoom;
                ro.getPixel(x, y, rgb);

                for (int x1 = xAmp; x1 < xAmp + zoom; x1++) {
                    for (int y1 = yAmp; y1 < yAmp + zoom; y1++) {
                        raster.setPixel(x1, y1, rgb);
                    }
                }
            }
        }

        return processada;
    }

    public BufferedImage zoomOut(BufferedImage original, int zoom) {

        BufferedImage processada = new BufferedImage(original.getWidth() / zoom, original.getHeight() / zoom, BufferedImage.TYPE_INT_RGB);
        //joga a imagem processada para dentro da variavel processada
        int h = original.getHeight();
        int w = original.getWidth();

        int[] pixels = new int[h * w * 3];

        WritableRaster raster = processada.getRaster();
        original.getRaster().getPixels(0, 0, w, h, pixels);
        WritableRaster ro = original.getRaster();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int[] rgb = new int[3];
                int xAmp = x * zoom;
                int yAmp = y * zoom;
                ro.getPixel(x, y, rgb);

                for (int x1 = xAmp; x1 <= xAmp + zoom; x1++) {
                    for (int y1 = yAmp; y1 <= yAmp + zoom; y1++) {
                        raster.setPixel(x1, y1, rgb);
                    }
                }
            }
        }

        return processada;
    }
    
    public BufferedImage createImagemByMatriz(Integer matriz[][][]){
        
        int h = matriz.length;
        int w = matriz[0].length;
        
        BufferedImage processada = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = processada.getRaster();
        
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int px[] = new int[3];
                for(int i=0;i<px.length;i++){
                    if(matriz[x][y][i] != null){
                        px[i] = matriz[x][y][i];
                        continue;
                    }
                    px[i] = 255;//se for nulo o px, seta como preto
                }
                raster.setPixel(x, y, px);
            }
        }
        processada.setData(raster);
        return processada;
    }

}
