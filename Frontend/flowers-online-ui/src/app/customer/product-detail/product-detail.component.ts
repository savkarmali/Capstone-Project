import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { ShopProduct, ShopService } from '../../services/shop.service';

interface SizeOption {
  label: string;
  price: number;
}

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  product: ShopProduct | null = null;
  sizeOptions: SizeOption[] = [];
  selectedSize: SizeOption | null = null;
  customerEmail = '';
  quantity = 1;
  isLoading = false;
  isAddingToCart = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private shopService: ShopService,
    private cartService: CartService
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

  addToCart(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (!this.product || !this.selectedSize) {
      this.errorMessage = 'Please select a product size.';
      return;
    }

    this.isAddingToCart = true;
    this.cartService.addToCart({
      customerEmail: this.customerEmail,
      productId: this.product.id,
      selectedSize: this.selectedSize.label,
      quantity: this.quantity
    }).subscribe({
      next: response => {
        this.successMessage = `${response.productName} added to cart successfully.`;
        this.isAddingToCart = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isAddingToCart = false;
      }
    });
  }

  getTotal(): number {
    return this.selectedSize ? this.selectedSize.price * this.quantity : 0;
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

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to add product to cart. Please try again.';
  }
}
