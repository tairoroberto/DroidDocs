package br.com.trmasolucoes.droiddocs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import br.com.trmasolucoes.droiddocs.dao.FavoritesDao;
import br.com.trmasolucoes.droiddocs.domain.Favorite;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** The WebView thus will show the page */
        webView = (WebView) findViewById(R.id.webView);

        /** Setting to wwebview*/
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);

        if (webView.getUrl() == null){
            webView.loadUrl("http://developer.android.com/");
        }

        /** Verifico se veio alguma url dos favoritos */
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                webView.loadUrl(bundle.getString("link"));
            }
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // Set up the input
                final EditText input = new EditText(MainActivity.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("ADICIONE UM TÍTULO!")
                        .setTitle("FAVORITOS")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /** Adiciona a página aso favoritos*/
                                FavoritesDao favoritesDao = new FavoritesDao(MainActivity.this);

                                Favorite favorite = favoritesDao.getFavotiteByLink(webView.getUrl());
                                if (favorite.getLink() != null){
                                    Toast.makeText(MainActivity.this, "Url já existe!", Toast.LENGTH_SHORT).show();
                                }else {
                                    favorite.setDesc(input.getText().toString());

                                    /** Verifico se a descição é válida */
                                    if (favorite.getDesc() == null || favorite.getDesc().equals("")){
                                        Toast.makeText(MainActivity.this, "Insira um título para o favorito!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        favorite.setLink(webView.getUrl());
                                        favoritesDao.insert(favorite);
                                        Toast.makeText(MainActivity.this, "FAVORITO ADICIONADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorites) {
            Intent intent = new Intent(MainActivity.this, FavoritesList.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
