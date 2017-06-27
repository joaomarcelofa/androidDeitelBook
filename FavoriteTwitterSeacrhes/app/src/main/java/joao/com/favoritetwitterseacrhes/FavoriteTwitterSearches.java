package joao.com.favoritetwitterseacrhes;

import java.util.Arrays;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class FavoriteTwitterSearches extends AppCompatActivity {

    private SharedPreferences savedSearches;
    private TableLayout queryTableLayout;
    private EditText queryEditText;
    private EditText tagEditText;

    private View.OnClickListener saveButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            if(queryEditText.getText().length() > 0 && tagEditText.getText().length() > 0){

                // Se a query e atag forem validas, crio a tag
                makeTag(queryEditText.getText().toString(), tagEditText.getText().toString());
                queryEditText.setText("");
                tagEditText.setText("");

                // Oculta o teclado virtual
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(tagEditText.getWindowToken(), 0);
            }
            else{

                // Configurando o alertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteTwitterSearches.this);
                builder.setTitle(R.string.missingTitle);
                builder.setPositiveButton(R.string.OK,null);
                builder.setMessage(R.string.missingMessage);

                // Criando o alert mesmo
                AlertDialog errorDialog = builder.create();
                errorDialog.show();

            }

        }
    };

    private View.OnClickListener clearTagsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteTwitterSearches.this);

            builder.setTitle(R.string.confirmTitle);

            builder.setPositiveButton(R.string.OK,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int button){
                    clearButtons();

                    SharedPreferences.Editor editor = savedSearches.edit();
                    editor.clear();// Remove todos os pares tag/consulta
                    editor.apply();// Aplica a modificação
                }
            });

            builder.setCancelable(true);
            // Nada acontece se ele cancelar
            builder.setNegativeButton(R.string.cancel,null);

            builder.setMessage(R.string.confirmMessage);

            AlertDialog confirmDialog = builder.create();
            confirmDialog.show();
        }
    };

    private View.OnClickListener queryButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            //Casting da view como Button e ai pegamos o texto
            String buttonText = ((Button)v).getText().toString();
            String query = savedSearches.getString(buttonText,null);

            String urlString = getString(R.string.searchURL) + query;

            Intent getURL = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));

            startActivity(getURL);

        }
    };

    private View.OnClickListener newEditButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            TableRow buttonTableRow = (TableRow) v.getParent();
            Button searchButton = (Button) buttonTableRow.findViewById(R.id.newTagButton);

            String tag = searchButton.getText().toString();

            tagEditText.setText(tag);
            queryEditText.setText(savedSearches.getString(tag,null));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        // Pegamos as preferencias do usuário se tiver no arquivo searches
        // e com a permissão privada, só esse app ir´acessar esse arquivo
        savedSearches = getSharedPreferences("searches",MODE_PRIVATE);

        // Vinculando os componentes do layout XML em Objetos
        queryTableLayout = (TableLayout) findViewById(R.id.queryTableLayout);
        queryEditText = (EditText) findViewById(R.id.queryEditText);
        tagEditText = (EditText) findViewById(R.id.tagEditText);

        // Vinculando os botões da interface a objetos do tipo botão
        // Adicionando comportamento aos botões quando clicados
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonListener);
        Button clearTagsButton = (Button) findViewById(R.id.clearTagsButton);
        clearTagsButton.setOnClickListener(clearTagsButtonListener);

        refreshButtons(null);

    }

    private void refreshButtons(String newTag){

        String[] tags = savedSearches.getAll().keySet().toArray(new String[0]);
        Arrays.sort(tags,String.CASE_INSENSITIVE_ORDER);

        // Se a tag for nula, quer dizer que não tenho um novo registro
        // Caso contrario eu insiro ela na posição correta pra manter a integridade da lista
        if(newTag != null){
            makeTagGUI(newTag, Arrays.binarySearch(tags,newTag));
        }
        else{
            for(int index = 0; index < tags.length; ++index){
                makeTagGUI(tags[index],index);
            }
        }
    }


    private void makeTag(String query, String tag){

        // Essa string procura a busca que selecionamos.
        // Caso ela exista, então achamos a tag, caso não, ela retorna null
        String originalQuery = savedSearches.getString(tag, null);

        // Para editar as sharedPreferences, eu preciso de um objeto específico, o Editor
        SharedPreferences.Editor preferenceEditor = savedSearches.edit();
        preferenceEditor.putString(tag, query); // Armazena a pesquisa
        preferenceEditor.apply();

        // Se é uma tag nova, eu tenho que dar refresh nos botões
        if (originalQuery == null) refreshButtons(tag);

    }

    private void makeTagGUI(String tag, int index){

        // Adquirindo o serviço de layoutInflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newTagView = inflater.inflate(R.layout.new_tag_view,null);

        Button newTagButton = (Button) newTagView.findViewById(R.id.newTagButton);
        newTagButton.setText(tag);
        newTagButton.setOnClickListener(queryButtonListener);

        Button newEditButton = (Button) newTagView.findViewById(R.id.newEditButton);
        newEditButton.setOnClickListener(newEditButtonListener);

        // Adiciona a nova linha na tabela
        queryTableLayout.addView(newTagView,index);
    }

    private void clearButtons(){
        // Remove todos os componentes de botão
        queryTableLayout.removeAllViews();
    }

}
