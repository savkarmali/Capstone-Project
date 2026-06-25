import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ShopProduct, ShopService } from '../../services/shop.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.css'
})
export class ShopComponent implements OnInit {
  categories: string[] = [];
  products: ShopProduct[] = [];
  selectedCategory = 'All';
  sortBy = 'new-arrivals';
  isLoading = false;
  errorMessage = '';

  constructor(private shopService: ShopService) { }

  ngOnInit(): void {
    this.loadCategories();
    this.loadProducts();
  }

  loadCategories(): void {
    this.shopService.getCategories().subscribe({
      next: categories => this.categories = categories,
      error: () => this.errorMessage = 'Unable to load categories.'
    });
  }

  loadProducts(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.shopService.getProducts(this.selectedCategory, this.sortBy).subscribe({
      next: products => {
        this.products = products;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load products.';
        this.isLoading = false;
      }
    });
  }

  selectCategory(category: string): void {
    this.selectedCategory = category;
    this.loadProducts();
  }

  shopNow(): void {
    this.selectedCategory = 'All';
    this.loadProducts();
  }

  getStartingPrice(product: ShopProduct): number | null {
    const prices = [product.smallPrice, product.mediumPrice, product.largePrice]
      .filter((price): price is number => price !== null && price !== undefined);
    return prices.length > 0 ? Math.min(...prices) : null;
  }


}
