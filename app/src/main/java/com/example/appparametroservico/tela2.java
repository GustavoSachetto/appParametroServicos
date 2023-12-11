package com.example.appparametroservico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class  tela2 extends AppCompatActivity {

    TextView txNome, txEndereco, txEmail, txTel;
    String nomeRecebido, enderecoRecebido, emailRecebido, telRecebido;

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);

        Intent telaAtual = getIntent();
        Bundle valores = telaAtual.getExtras();

        nomeRecebido = valores.getString("nome");
        enderecoRecebido = valores.getString("endereco");
        emailRecebido = valores.getString("email");
        telRecebido = valores.getString("tel");

        txNome = findViewById(R.id.txtNome);
        txEndereco = findViewById(R.id.txtEndereco);
        txEmail = findViewById(R.id.txtEmail);
        txTel = findViewById(R.id.txtTel);

        txNome.setText(nomeRecebido);
        txEndereco.setText(enderecoRecebido);
        txEmail.setText(emailRecebido);
        txTel.setText(telRecebido);

    }


    public void fazerLigacao (View v) {
    //CONTRUIR MENSAGEM
        AlertDialog.Builder mensagem = new AlertDialog.Builder(tela2.this);
        //TITULO
        mensagem.setTitle("Opções");
        //TEXTO DA MENSAGEM
        mensagem.setMessage("O que você deseja fazer?");
        //BOTÃO POSITIVO PARA FAZER LIGAÇÃO
        mensagem.setPositiveButton("Ligar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //COMANDOS PARA FAZER LIGAÇÃO

                //FORMATANDO PARA O PADRÃO DA LIGAÇÃO
                Uri uri = Uri.parse("tel:"+telRecebido);
                //PASSANDO NUMERO FORMATADO PARA A INTENT DE LIGAÇÃO
                Intent ligar = new Intent(Intent.ACTION_DIAL,uri);
                //EXECUTANDO A INTENT DE LIGAÇÃO
                startActivity(ligar);
            }
        });

        //BOTÃO NEGATIVO PARA ABRIR WHATSAPP
        mensagem.setNegativeButton("Whatsapp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //COMANDOS PARA ABRIR O WHATSAPP

                //FORMATANDO PARA O PADRAO DO WHATSAPP
                String contato = "+55" + telRecebido; // +55 CODIGO DO BRASIL
                //ACESSANDO O SERVIÇO DO WHATSAPP
                String url = "https://api.whatsapp.com/send?phone=" + contato;
                //VERIFICAÇÃO
                try {
                //SE TIVER INSTALADO
                    PackageManager pm = getPackageManager();
                    //PROCURANDO PELO PACOTE DO WHATSAPP NO CELULAR
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    //PREPARANDO A ABERTURA DO WHATSAPP
                    Intent whatsapp = new Intent(Intent.ACTION_VIEW);
                    //PASSANDO O ENDEREÇO FORMATADO PARA A INTENT DO WHATSAPP
                    whatsapp.setData(Uri.parse(url));
                    //EXECUTANDO A INTENT DE WHATSAPP
                    startActivity(whatsapp);
                } catch (PackageManager.NameNotFoundException erro) {
                //SE NÃO TIVER INSTALADO
                    Toast.makeText(tela2.this, "Whatsapp não está instalado neste telefone",Toast.LENGTH_SHORT).show();
                    erro.printStackTrace();
                    try {
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        mp.reset();
                        AssetFileDescriptor afd = null;
                        afd = getResources().openRawResourceFd(R.raw.erro);

                        if (afd != null) {
                            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                            mp.prepareAsync();
                        }
                    } catch (IOException e) {
                        Log.e("Erro", erro.getMessage());
                    }
                }
            }
        });

        //BLOQUEAR A MENSAGEM
        mensagem.setCancelable(false);

        //AUDIO ERRO
        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mp.start();
            }
        });

        //ADICIONAR ICONE
        mensagem.setIcon(R.drawable.icone);
        //MOSTRAR A MENSAGEM
        mensagem.show();

    }

    public void enviarEmail (View v) {
       //QUEM RECEBE O E-MAIL
       String mailto = "mailto:"+ emailRecebido +
       //QUEM RECEBE UMA COPIA (OPICIONAL)
       "?cc="+ "" +
       //ASSUNTO DO E-MAIL
       "&subject=" + Uri.encode("E-mail de teste pelo app") +
       //CORPO/MENSAGEM
       "&body=" + Uri.encode("");
       //PREPARANDO A ABERTURA DO E-MAIL
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        //EXECUTANDO A INTENT E-MAIL
        emailIntent.setData(Uri.parse(mailto));

        //CASO DÊ ERRO AO CRIAR O E-MAIL
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is
            Toast.makeText(this, "Erro ao enviar o e-mail" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirMapa (View v) {
        Uri gmmIntent = Uri.parse("geo:0,0?q=" + enderecoRecebido);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntent);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}