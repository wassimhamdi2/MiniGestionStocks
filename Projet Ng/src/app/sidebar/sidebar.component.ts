import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; 
import { ServiceService, Produit } from '../service.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule


  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  products: Produit[] = [];

  constructor(private productService: ServiceService) {}

  ngOnInit(): void {
    this.loadProducts(); // Call to load products
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        console.error('Error fetching products:', err);
      },
    });
  }

}
