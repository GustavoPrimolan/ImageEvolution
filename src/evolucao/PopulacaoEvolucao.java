package evolucao;

import bean.Individuo;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.List;

public class PopulacaoEvolucao {

    public PopulacaoEvolucao() {

    }

    public List<Individuo> fitness(List<Individuo> populacao, int geracao) {
        List<Individuo> selecao = new ArrayList<>();

        int indiceVet[] = new int[4];
        //FAZ A SELACAO DOS DOIS MELHORES INDIVÍDUOS
        for (int i = 0; i < 4; i++) {
            int maior = 0;
            int indice = 0;
            for (int j = 0; j < populacao.size(); j++) {
                if (maior <= populacao.get(j).getPixelsIguais()) {
                    boolean temIndice = false;
                    for (int k = 0; k < indiceVet.length; k++) {
                        if (indiceVet[k] == j) {
                            temIndice = true;
                            break;
                        }
                    }
                    if (temIndice == false) {
                        maior = populacao.get(j).getPixelsIguais();
                        indice = j;
                    }

                }
            }
            indiceVet[i] = indice;

            selecao.add(populacao.get(indiceVet[i]));
        }

        for (int w = 0; w < indiceVet.length; w++) {
            System.out.println("Posição dos melhores individuos: " + (indiceVet[w] + 1));
        }

        System.out.println("Melhor indivíduo: " + populacao.get(indiceVet[0]).getPixelsIguais());

        if (geracao % 2 == 0 || geracao == 1) {
            IJ.save(populacao.get(indiceVet[0]).getImg(), "Geracao - " + geracao + " - Indivíduo - " + indiceVet[0] + ".png");
        }
        /*
         if(geracao % 1000 ==0)
         for (int j = 0; j < populacao.get(indiceVet[0]).getIp().getWidth(); j++) {
         for (int k = 0; k < populacao.get(indiceVet[0]).getIp().getHeight(); k++) {
         System.out.println(populacao.get(indiceVet[0]).getIp().getPixel(j, k));
         }
         }
         */

        return selecao;
    }

    public List<Individuo> cruzamento(List<Individuo> selecao, ImagePlus imgAlterada) {
        List<Individuo> novosIndividuos = new ArrayList<Individuo>();
        Individuo novoIndividuo = null;
        int par = 0;
        int filho[] = new int[selecao.size()];
        //FAZ O CRUZAMENTO DO PAI UM COM PAI DOIS E GERA DOIS FILHOS
        for (int i = 0; i < selecao.size(); i++) {
            novoIndividuo = new Individuo(imgAlterada.duplicate());
            boolean iPar = false;
            if (i % 2 == 0) {
                par = i;
                iPar = true;
            }
            for (int j = 0; j < selecao.get(par).getIp().getWidth(); j++) {
                for (int k = 0; k < selecao.get(par).getIp().getHeight(); k++) {
                    int pai = -1;
                    int paiUm = 0;
                    int paiDois = 0;
                    if (selecao.get(par).getPixelIgual(j, k) == true) {
                        pai = selecao.get(par).getIp().getPixel(j, k);
                    } else if (selecao.get(par + 1).getPixelIgual(j, k) == true) {
                        pai = selecao.get(par + 1).getIp().getPixel(j, k);
                    } else {
                        int sorteioPixelLargura = (int) (Math.random() * (selecao.get(par).getIp().getWidth() - 1));
                        int sorteioPixelAltura = (int) (Math.random() * (selecao.get(par + 1).getIp().getHeight() - 1));
                        paiUm = selecao.get(par).getIp().getPixel(sorteioPixelLargura, sorteioPixelAltura);
                        paiDois = selecao.get(par + 1).getIp().getPixel(sorteioPixelLargura, sorteioPixelAltura);
                        //paiUm = selecao.get(par).getIp().getPixel(j, k);
                        //paiDois = selecao.get(par + 1).getIp().getPixel(j, k);
                    }
                    //int sorteioPixelLargura = (int) (Math.random() * (selecao.get(par).getIp().getWidth()-1));
                    //int sorteioPixelAltura = (int) (Math.random() * (selecao.get(par + 1).getIp().getHeight()-1));
                    //int paiUm = selecao.get(par).getIp().getPixel(sorteioPixelLargura, sorteioPixelAltura);
                    //int paiDois = selecao.get(par + 1).getIp().getPixel(sorteioPixelLargura, sorteioPixelAltura);

                    //int paiUm = selecao.get(par).getIp().getPixel(j, k);
                    //int paiDois = selecao.get(par + 1).getIp().getPixel(j, k);
                    int sorteio = (int) Math.random();
                    if (iPar == true) {
                        if (pai != -1) {
                            filho[i] = pai;
                        } else if (sorteio == 0) {
                            filho[i] = paiUm;
                        } else {
                            filho[i] = paiDois;
                        }
                    } else if (iPar == false) {
                        if (pai != -1) {
                            filho[i] = pai;
                        } else if (sorteio != 0) {
                            filho[i] = paiUm;
                        } else {
                            filho[i] = paiDois;
                        }
                    };
                    novoIndividuo.getIp().putPixel(j, k, filho[i]);
                }

            }

            System.out.println("Filho " + (i + 1) + " " + novoIndividuo.getIp().getPixel(5, 5));
            novosIndividuos.add(novoIndividuo);
        }

        return novosIndividuos;
    }

    public void multacao(Individuo individuo, int geracao) {
        int probSubtracao = (int) (Math.random() * 100);
        System.out.println("Resultado da probabilidade de subtracao: " + probSubtracao);
        int pixelMutado = 0;
        for (int i = 0; i < individuo.getIp().getWidth(); i++) {
            for (int j = 0; j < individuo.getIp().getHeight(); j++) {
                /*
                 if (probSubtracao <= 50) {
                 if (j % 2 == 0) {
                 //pixelMutado = individuo.getIp().getPixel(i, j) + individuo.getIp().getPixel(i, j + 1);
                 pixelMutado = individuo.getIp().getPixel(i, j) + 12;
                 } else {
                 pixelMutado = Math.abs(individuo.getIp().getPixel(i, j) - 12);
                 }
                 } else {
                 if (j % 2 == 0) {
                 //pixelMutado = Math.abs(individuo.getIp().getPixel(i, j) - individuo.getIp().getPixel(i, j+1));
                 pixelMutado = Math.abs(individuo.getIp().getPixel(i, j) - 13);
                 } else {
                 //pixelMutado = individuo.getIp().getPixel(i, j) + individuo.getIp().getPixel(i, j-1);
                 pixelMutado = individuo.getIp().getPixel(i, j) + 13;
                 }
                 }
                 */
                //if(pixelMutado > 255){
                int probBrancoPreto = (int) Math.random();
                if (probBrancoPreto % 2 == 0) {
                    pixelMutado = (int) (Math.random() * (200 - 0) + 200);
                } else {
                    pixelMutado = 255;
                }
                //}
                /*
                 if (pixelMutado > 255 || pixelMutado < 0) {
                 if (probBrancoPreto % 2 == 0) {
                 pixelMutado = (int) (Math.random() * (200 - 0) + 200);
                 } else {
                 pixelMutado = 255;
                 }
                 }
                 */
                individuo.getIp().putPixel(i, j, pixelMutado);
            }
        }

    }

    public void geraNovaPopulacao(List<Individuo> novosIndividuos, List<Individuo> populacao, int geracao) {
        System.out.println("Gera nova população com filho 1: " + novosIndividuos.get(0).getIp().getPixel(5, 5));
        System.out.println("Gera nova população com filho 2: " + novosIndividuos.get(1).getIp().getPixel(5, 5));
        System.out.println("Gera nova população com filho 3: " + novosIndividuos.get(2).getIp().getPixel(5, 5));
        System.out.println("Gera nova população com filho 4: " + novosIndividuos.get(3).getIp().getPixel(5, 5));
        //FAZ A SELACAO DOS DOIS PIORES INDIVÍDUOS E OS REMOVE
        for (int i = 0; i < novosIndividuos.size(); i++) {
            int menor = populacao.get(i).getPixelsIguais();
            int indice = 0;
            for (int j = 0; j < populacao.size(); j++) {
                if (menor >= populacao.get(j).getPixelsIguais()) {

                    menor = populacao.get(j).getPixelsIguais();
                    indice = j;
                }
            }
            System.out.println("Piores individuos: " + ((indice + 1)));

            populacao.remove(indice);
        }

        int indiceVet[] = new int[4];
        for (int i = 0; i < 2; i++) {
            int probabilidadeMutacao = (int) (Math.random() * 100);
            int menor = populacao.get(i).getPixelsIguais();
            int indice = 0;
            for (int j = 0; j < populacao.size(); j++) {
                if (menor >= populacao.get(j).getPixelsIguais()) {
                    boolean temIndice = false;
                    for (int k = 0; k < indiceVet.length; k++) {
                        if (indiceVet[k] == j) {
                            temIndice = true;
                            break;
                        }
                        if (temIndice == false) {
                            menor = populacao.get(j).getPixelsIguais();
                            indice = j;
                        }
                    }

                }
            }
            indiceVet[i] = indice;
            System.out.println("Indivíduo para gerar mutacao " + indiceVet[i]);

            if (probabilidadeMutacao <= 30) {
                System.out.println("Individuo Mutou");
                multacao(populacao.get(indiceVet[i]), geracao);
            }
        }
        //COLOCA OS NOVOS FILHOS NA POPULAÇÃO
        for (Individuo filho : novosIndividuos) {
            populacao.add(filho);
        }

    }

    //VERIFICA SE ALGUM DOS INDIVIDUOS POSSUE TODOS OS PIXELS IGUAIS PARA ENTÃO COMPLETAR O OBJETIVO
    public boolean chegouObjetivo(List<Individuo> populacao, ImageProcessor ipObjetivo, int geracao) {
        boolean objetivoConcluido = false;

        //VERIFICA OS PIXELS IGUAIS DE CADA INDIVÍDUO
        int contIndi = 1;
        for (Individuo indi : populacao) {
            int sPixelsIguais = 0;
            System.out.println("Individuo " + contIndi);
            contIndi++;
            for (int i = 0; i < indi.getIp().getWidth(); i++) {
                for (int j = 0; j < indi.getIp().getHeight(); j++) {
                    if (geracao % 1000 == 0) {
                        //System.out.println("Valor Pixel Individuo: " + indi.getIp().getPixel(i, j));
                        //System.out.println("Valor Pixel ImagemObjetivo: " + ipObjetivo.getPixel(i, j));
                    }
                    if (indi.getIp().getPixel(i, j) == ipObjetivo.getPixel(i, j)) {
                        indi.setPixelIgual(i, j, true);
                        sPixelsIguais++;
                    } else {
                        indi.setPixelIgual(i, j, false);
                    }

                }
            }
            indi.setPixelsIguais(sPixelsIguais);
            System.out.println("Quantidade de Pixels Iguals do Individuo: " + indi.getPixelsIguais());
            System.out.println("");
        }

        int somatorio = 0;
        //FAZ A MÉDIA DA POPULAÇÃO
        System.out.println("Tamanho da população: " + populacao.size());
        for (Individuo indi : populacao) {
            somatorio = somatorio + indi.getPixelsIguais();
        }

        int media = somatorio / populacao.size();
        System.out.println("A média de pixels iguais a imagem original é: " + media);

        //VERIFICA SE ALGUM INDIVÍDUO É IGUAL A IMAGEM OBJETIVO
        for (Individuo indi : populacao) {
            if (indi.getPixelsIguais() == ipObjetivo.getWidth() * ipObjetivo.getHeight()) {
                objetivoConcluido = true;
                indi.getImg().show();
                break;
            }
        }

        return objetivoConcluido;
    }
}
