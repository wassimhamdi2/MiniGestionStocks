import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProduitComponent } from './produit/produit.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StockComponent } from './stock/stock.component';
import { AjoutproduitComponent } from './ajoutproduit/ajoutproduit.component';

export const routes: Routes = [
  { 
    path: '', 
    redirectTo: 'Home', 
    pathMatch: 'full' 
  },
  { 
    path: 'Home',
    component: HomeComponent,
    children: [
      {
        path: '',
        redirectTo: 'Dashboard',
        pathMatch: 'full'
      },
      {
        path: 'Dashboard',
        component: DashboardComponent,
        pathMatch: 'full'
      },
      {
        path: 'Produit',
        component: ProduitComponent,
        runGuardsAndResolvers: 'always',  // Forces re-initialization
      },
      {
        path: 'Stock',
        component: StockComponent,
      },
      {
        path: 'AddProduit',
        component: AjoutproduitComponent,
      },
    ]
  },
  { path: '**', redirectTo: 'Home' }
];
