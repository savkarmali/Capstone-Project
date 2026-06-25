import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ShopProduct, ShopService } from '../../services/shop.service';

interface SizeOption {
  label: string;
  price: number;
}

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  product: ShopProduct | null = null;
  sizeOptions: SizeOption[] = [];
  selectedSize: SizeOption | null = null;
  isLoading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private shopService: ShopService
  ) { }

  ngOnInit(): void {
    const productId = Number(this.route.snapshot.paramMap.get('id'));

    if (!productId) {
      this.errorMessage = 'Invalid product selected.';
      return;
    }

    this.loadProduct(productId);
  }

  loadProduct(productId: number): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.shopService.getProductById(productId).subscribe({
      next: product => {
        this.product = product;
        this.sizeOptions = this.getSizeOptions(product);
        this.selectedSize = this.sizeOptions.length > 0 ? this.sizeOptions[0] : null;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load product details.';
        this.isLoading = false;
      }
    });
  }

  selectSize(sizeOption: SizeOption): void {
    this.selectedSize = sizeOption;
  }

  private getSizeOptions(product: ShopProduct): SizeOption[] {
    const options: SizeOption[] = [];

    if (product.smallPrice !== null) {
      options.push({ label: 'Small', price: product.smallPrice });
    }

    if (product.mediumPrice !== null) {
      options.push({ label: 'Medium', price: product.mediumPrice });
    }

    if (product.largePrice !== null) {
      options.push({ label: 'Large', price: product.largePrice });
    }

    return options;
  }
}
