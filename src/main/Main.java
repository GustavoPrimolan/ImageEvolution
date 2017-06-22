package main;

import bean.Individuo;
import evolucao.PopulacaoEvolucao;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.ContrastEnhancer;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        ImagePlus img = ij.IJ.openImage();
        ImageConverter ic = new ImageConverter(img);
        ic.convertToGray8();
        img.updateAndDraw();

        ImagePlus imgObjetivo = img.duplicate();
        ImageProcessor ipObjetivo = img.getProcessor();
        
        ContrastEnhancer ce = new ContrastEnhancer();
        ce.equalize(imgObjetivo);
        imgObjetivo.updateAndDraw();
        
        IJ.save(imgObjetivo, "Imagem Objetivo.png");
        
        
        Individuo individuo = null;

        PopulacaoEvolucao populacaoEvolucao = new PopulacaoEvolucao();

        System.out.println("Largura da imagem: " + ipObjetivo.getWidth());
        System.out.println("Altura da imagem: " + ipObjetivo.getHeight());

        ImagePlus imgAlterada = null;
        ImageProcessor ipAlterada = null;

        List<Individuo> populacao = new ArrayList<>();
        
        //GERA POPULAÇÃO ALEATÓRIA
        for (int i = 0; i < 10; i++) {
            imgAlterada = imgObjetivo.duplicate();
            ipAlterada = imgAlterada.getProcessor();
            //CRIA A POPULAÇÃO COM PIXELS ALEATÓRIOS
            for (int j = 0; j < ipAlterada.getWidth(); j++) {
                for (int k = 0; k < ipAlterada.getHeight(); k++) {
                    int pixelAleatorio = (int) (Math.random() * 255);
                    ipAlterada.putPixel(j, k, pixelAleatorio);
                }
            }
            
            individuo = new Individuo(imgAlterada);
            populacao.add(individuo);

        }

        System.out.println("Quantidade de pixels da imagem objetivo é: " + ipObjetivo.getWidth() * ipObjetivo.getHeight());

        List<Individuo> fitness = null;
        List<Individuo> filhosCruzamento = null;
        //OBJETIVO
        int geracao = 1;
        while (populacaoEvolucao.chegouObjetivo(populacao, ipObjetivo, geracao) == false) {
            int contIndividuo = 1;
            fitness = populacaoEvolucao.fitness(populacao, geracao);
            System.out.println("GEROU FITNESS!!");
            filhosCruzamento = populacaoEvolucao.cruzamento(fitness, imgAlterada.duplicate());
            System.out.println("GEROU NOVOS FILHOS!!");
            populacaoEvolucao.geraNovaPopulacao(filhosCruzamento, populacao, geracao);
            System.out.println("GEROU NOVA POPULAÇÃO");
            System.out.println("");
            
            geracao++;
        }

        imgObjetivo.show();

    }

}
