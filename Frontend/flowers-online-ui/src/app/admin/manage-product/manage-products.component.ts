import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductRequest, ProductResponse, ProductService } from '../../services/product.service';

@Component({
  selector: 'app-manage-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-products.component.html',
  styleUrl: './manage-products.component.css'
})
export class ManageProductsComponent implements OnInit {
  categories = ['All', 'Birthday', 'Love', 'Marriage', 'Grand-Opening', 'Sympathy', 'Get-well-soon'];

  products: ProductResponse[] = [];
  selectedProductId: number | null = null;
  editProduct: ProductRequest = this.getEmptyProduct();
  successMessage = '';
  errorMessage = '';
  isLoading = false;
  isSaving = false;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.isLoading = true;
    this.successMessage = '';
    this.errorMessage = '';

    this.productService.getProducts().subscribe({
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

  startEdit(product: ProductResponse): void {
    this.selectedProductId = product.id;
    this.editProduct = {
      name: product.name,
      description: product.description,
      category: product.category,
      imageUrl: product.imageUrl,
      smallPrice: product.smallPrice,
      mediumPrice: product.mediumPrice,
      largePrice: product.largePrice,
      stockQuantity: product.stockQuantity,
      available: product.available
    };
    this.successMessage = '';
    this.errorMessage = '';
  }

  cancelEdit(): void {
    this.selectedProductId = null;
    this.editProduct = this.getEmptyProduct();
  }

  updateProduct(): void {
    if (this.selectedProductId === null) {
      this.errorMessage = 'Please select a product to update.';
      return;
    }

    if (!this.hasAtLeastOnePrice()) {
      this.errorMessage = 'Enter at least one price: small, medium, or large.';
      return;
    }

    this.isSaving = true;
    this.productService.updateProduct(this.selectedProductId, this.editProduct).subscribe({
      next: response => {
        this.successMessage = `Product "${response.name}" updated successfully.`;
        this.cancelEdit();
        this.loadProducts();
        this.isSaving = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isSaving = false;
      }
    });
  }

  deleteProduct(product: ProductResponse): void {
    const shouldDelete = confirm(`Delete product "${product.name}"?`);
    if (!shouldDelete) {
      return;
    }

    this.productService.deleteProduct(product.id).subscribe({
      next: () => {
        this.successMessage = `Product "${product.name}" deleted successfully.`;
        this.loadProducts();
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
      }
    });
  }

  private hasAtLeastOnePrice(): boolean {
    return this.editProduct.smallPrice !== null || this.editProduct.mediumPrice !== null || this.editProduct.largePrice !== null;
  }

  private getEmptyProduct(): ProductRequest {
    return {
      name: '',
      description: '',
      category: '',
      imageUrl: '',
      smallPrice: null,
      mediumPrice: null,
      largePrice: null,
      stockQuantity: 0,
      available: true
    };
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to complete the action. Please try again.';
  }
}
