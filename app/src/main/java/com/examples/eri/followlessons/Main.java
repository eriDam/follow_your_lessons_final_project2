package com.examples.eri.followlessons;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class Main extends Activity {

    /*
     DECLARACIONES
     */
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemTitle = activityTitle = getTitle();
        tagTitles = getResources().getStringArray(R.array.Tags);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        /*Tendré que llamar a los fragments
        * .......
        *
        * **/


        // Setear una sombra sobre el contenido principal cuando el drawer se despliegue
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Crear elementos de la lista
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(tagTitles[0],R.mipmap.ic_git));
        items.add(new DrawerItem(tagTitles[1],R.mipmap.ic_cpp));
        items.add(new DrawerItem(tagTitles[2],R.mipmap.ic_qt));
        items.add(new DrawerItem(tagTitles[3],R.mipmap.ic_html_five));
        items.add(new DrawerItem(tagTitles[4],R.mipmap.ic_css_tres));
        items.add(new DrawerItem(tagTitles[5],R.mipmap.ic_js));
        items.add(new DrawerItem(tagTitles[6],R.mipmap.ic_jquery));
        items.add(new DrawerItem(tagTitles[7],R.mipmap.ic_ajax));
        items.add(new DrawerItem(tagTitles[8],R.mipmap.ic_json));
        items.add(new DrawerItem(tagTitles[9],R.mipmap.ic_php));
        items.add(new DrawerItem(tagTitles[10],R.mipmap.ic_ruby));
        items.add(new DrawerItem(tagTitles[11],R.mipmap.ic_dw));
        items.add(new DrawerItem(tagTitles[12],R.mipmap.ic_drupal));
        items.add(new DrawerItem(tagTitles[13],R.mipmap.ic_fireworks));
        items.add(new DrawerItem(tagTitles[14],R.mipmap.ic_joomla));
        items.add(new DrawerItem(tagTitles[15],R.mipmap.ic_wordpress));
        items.add(new DrawerItem(tagTitles[16],R.mipmap.ic_mysql));
        items.add(new DrawerItem(tagTitles[17],R.mipmap.ic_seo));
        items.add(new DrawerItem(tagTitles[18],R.mipmap.ic_ps));


        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Habilitar el icono de la app por si hay algún estilo que lo deshabilitó
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

       /*
         Crear ActionBarDrawerToggle para la apertura y cierre:
         primer parámetro es el contexto donde se ejecuta. También necesita la instancia del DrawerLayout con que se
         relacionará. El tercer parámetro es el drawable que representará su presencia, en este caso es el recurso del
         sistema R.drawable.ic_drawer. Y por ultimo dos strings de accesibilidad que contienen la información sobre la
         apertura y cierre del Drawer.
         */
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(itemTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(activityTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }
        };
        //Seteamos la escucha
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            
        }
    }

    private void selectItem(int position) {
        // Reemplazar el contenido del layout principal por un fragmento
        Fragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_ARTICLES_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // Se actualiza el item seleccionado y el título, después de cerrar el drawer
        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    /* Método auxiliar para setear el titulo de la action bar */
    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getActionBar().setTitle(itemTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }
}