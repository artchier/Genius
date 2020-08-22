package com.example.diego.genius;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.String.valueOf;


public class Genius extends AppCompatActivity {

    // variaveis e objetos
    public int index = 0;
    private int cor;
    private int contador=0,contador2=0,cont3=0,pontos=1;
    private long velocidade = 450 , velocidade2=200,velocidade3=1500;
    private Random gerador = new Random();
    private Timer tempo = new Timer();
    private Timer tempo4 = new Timer();
    private int[] vetor = new int[200];
    int[] teste = {1,2,3,4};
    private int Cont=0,Cont2=0,contador3=0,i,indicadorDeFinal=1 , contadorDeAcertos =0,p;
    // contadorDeAcertos serve para garantir a piscagem dos quadrados, sem ele , quadrados repetidos nao pisca
    private ImageButton lacuna1; // amarelo
    private ImageButton lacuna2; // verde
    private ImageButton lacuna3; // azul
    private ImageButton lacuna4; // vermelho
    private ImageView off;
    private TextView Pontuacao;
    private TextView Proxima;
    private TextView Score;
    private AlertDialog mostrar;
    private acao accion;
    private Temposegundos intervalo;
    private Button restart;
    private Handler color = new Handler();
    private Runnable cores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genius);

        lacuna1 = findViewById(R.id.lacuna1);
        lacuna2 = findViewById(R.id.lacuna2);
        lacuna3 = findViewById(R.id.lacuna3);
        lacuna4 = findViewById(R.id.lacuna4);

        /*lacuna1.setClickable(false);
        lacuna2.setClickable(false);
        lacuna3.setClickable(false);
        lacuna4.setClickable(false);
        */
        off = findViewById(R.id.off);
        Pontuacao = findViewById(R.id.Pontuacao);
        Proxima = findViewById(R.id.Proxima);
        Score = findViewById(R.id.score);
        accion = new acao();
        intervalo = new Temposegundos();
        restart = findViewById(R.id.Start);
        cores = new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        off.setBackgroundResource(R.drawable.off);
                    }
                },1500);//1500 é o certo
                Log.e("teste","index primeiro: "+index);
                Log.e("teste","Acertos: "+contadorDeAcertos);
                if(teste[index]==1)
                    off.setBackgroundResource(R.drawable.yellow);
                if(teste[index]==2)
                    off.setBackgroundResource(R.drawable.green);
                if(teste[index]==3)
                    off.setBackgroundResource(R.drawable.blue);
                if(teste[index]==4)
                    off.setBackgroundResource(R.drawable.red);
                if(index==contadorDeAcertos) {
                    index = 0;
                    Log.e("teste","Passou aqui"+index);
                    lacuna1.setClickable(true);
                    lacuna2.setClickable(true);
                    lacuna3.setClickable(true);
                    lacuna4.setClickable(true);
                    color.removeCallbacks(this);
                }
                else{
                    index++;
                    indicadorDeFinal++;
                    color.postDelayed(this, 2000);//2000 é o certo
                }
            }
        };
        Log.e("teste", Arrays.toString(vetor));


        // ler o arquivo e mudar o placar de Score
        Score.setText(""+Ler());

        // fim de variaveis
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_genius, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void BotaoStart(View view){

        contador++;
        Pontuacao.setText(valueOf(contadorDeAcertos));
        restart.setText("Reiniciar");
        if(contador==1){
            EscolherPosicoes(); //sorteio posições
            cores.run(); //começa a piscar

            //accion = new acao();
            //tempo = new Timer();

            //tempo.schedule(accion,100,velocidade);
        }

        else{
            //tempo.cancel();
            Reiniciar();

        }

    }

    private void Reiniciar(){

        cores.run();
        contador2=0;
        contador3=0;
        off.setBackgroundResource(R.drawable.off);
        pontos=1;
        Pontuacao.setText("0");
        cont3=0;
        Cont=0;
        Cont2=0;
        EscolherPosicoes();
        //tempo=new Timer();
        //accion=new acao();
        indicadorDeFinal=1; // COLOCADA PARA CONTROLE DE VELOCIDADE DO CLIQUE PARA NÃO ENTRAR EM LOOP
        //tempo3.cancel(); // COLOCADA PARA CONTROLE DE VELOCIDADE DO CLIQUE PARA NÃO ENTRAR EM LOOP
        //tempo.schedule(accion, 300, velocidade);


    }

    private void ConfirmarJogada(){

        //intervalo=new Temposegundos(); // esse
        //tempo4= new Timer();           //esse
        Proxima.setText("   Aguarde");
        //tempo4.schedule(intervalo, 0, velocidade3);




    }

    private void EscolherPosicoes(){
        for(p = 0 ; p<200 ; p++){
            i = gerador.nextInt(4);
            vetor[p] = i+1;
        }
        Log.e("teste", Arrays.toString(vetor));
    }


// metodos das lacunas

    public void amarelo(View view){
        Log.e("teste","passou em amarelo");
        color.post(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.yellow);
            }
        });
        color.postDelayed(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.off);
            }
        },20);

        cor = 1;
        comparar();
    }

    public void verde(View view){
        Log.e("teste","passou em verde");
        color.post(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.green);
            }
        });
        color.postDelayed(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.off);
            }
        },20);

        cor = 2;
        comparar();
    }

    public void azul(View view){
        Log.e("teste","passou em azul");
        color.post(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.blue);
            }
        });
        color.postDelayed(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.off);
            }
        },20);

        cor = 3;
        comparar();
    }

    public void vermelho(View view){
        Log.e("teste","passou em vermelho");
        color.post(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.red);
            }
        });
        color.postDelayed(new Runnable() {
            @Override
            public void run() {
                off.setBackgroundResource(R.drawable.off);
            }
        },20);

        cor = 4;
        comparar();
    }

    public void comparar() {

        if(cor==teste[index]) {
            index++;
            Log.e("teste","index:"+index);
        }
        else{
            mensagem();
        }
        if(indicadorDeFinal==index){
            contadorDeAcertos++;
            index=0;
            Pontuacao.setText(valueOf(contadorDeAcertos));
            lacuna1.setClickable(false);
            lacuna2.setClickable(false);
            lacuna3.setClickable(false);
            lacuna4.setClickable(false);
            cores.run();
        }
    }

    // mensagem de fim de jogo

    public void mensagem(){

        AlertDialog.Builder mensage = new AlertDialog.Builder(this);
        mensage.setTitle("  :C  ");
        mensage.setMessage("Jogada Errado");
        mensage.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Reiniciar();
            }
        });

        mostrar = mensage.create();
        mostrar.show();


    }

    // analisando pontuaçao para poder gravar ou não no arquivo

    public void AnalisarPontos(int Ponto , int PontoGravado){
        if(Ponto>PontoGravado){
            Salvar(Ponto);
            Score.setText(""+Ponto);
        }
    }

    class acao extends TimerTask{

        public void run(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    //coresPadrao();
                    contadorDeAcertos++;

                    if(contadorDeAcertos % 2 ==0 )

                    {
                        indicadorDeFinal++;// variavel de controle para o usuario não clicar junto quando estiver em execuçao esse comando
                        if(contador2<=Cont)
                        {
                            switch (vetor[contador2])
                                {
                                case 0:

                                    lacuna1.setBackgroundColor(Color.parseColor("#ff0001"));
                                    off.setBackgroundResource(R.drawable.red);
                                    break;

                                case 1:

                                    lacuna2.setBackgroundColor(Color.parseColor("#26ff09"));
                                    off.setBackgroundResource(R.drawable.green);
                                    break;

                                case 2:

                                    lacuna3.setBackgroundColor(Color.parseColor("#101bff"));
                                    off.setBackgroundResource(R.drawable.blue);
                                    break;

                                case 3:

                                    lacuna4.setBackgroundColor(Color.parseColor("#fff006"));
                                    off.setBackgroundResource(R.drawable.yellow);
                                    break;
                            }

                            contador2++;
                        }

                        else
                        {
                            if(contador2>Cont)
                            {
                                contador2=0;
                                indicadorDeFinal=0;
                                contadorDeAcertos =0;
                                tempo.cancel();
                            }
                        }
                    }

                }
            });
        }
    }

    class Temposegundos extends TimerTask {
        public void run(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cont3++; //pontos
                    if(cont3==2){
                        AnalisarPontos(pontos,Ler());
                        Proxima.setText("        ");
                        Pontuacao.setText(" " + pontos);
                        pontos++;
                        Cont2=0;
                        Cont++;



                    }
                    if(cont3==3) {
                        cont3=0;
                        tempo=new Timer();
                        accion = new acao();
                        tempo.schedule(accion, 0, velocidade);
                        indicadorDeFinal=0; // COLOCADA PARA CONTROLE DE VELOCIDADE DO CLIQUE PARA NÃO ENTRAR EM LOOP
                        tempo4.cancel();
                    }
                }
            });
        }
    }


    // salvar e ler Pontuacao

    // salvar
    private void Salvar(int recorde){ // ta passando como parametro os pontos

        // criar um arquivo ou abrir

        SharedPreferences salvarELer = getSharedPreferences("pontuacao" , Context.MODE_PRIVATE);

        // editor

        SharedPreferences.Editor editar = salvarELer.edit();

        editar.putInt("ponts" , recorde);

        //gravar

        editar.commit();

    }

    // ler
    private int Ler(){

        SharedPreferences salvarELer = getSharedPreferences("pontuacao" , Context.MODE_PRIVATE);

        // pegando os pontos salvos

      int PegarSalvo=   salvarELer.getInt("ponts" ,0);

      return PegarSalvo;
    }
}
