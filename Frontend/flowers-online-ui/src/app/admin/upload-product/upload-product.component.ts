import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductRequest, ProductService } from '../../services/product.service';

@Component({
  selector: 'app-upload-product',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './upload-product.component.html',
  styleUrl: './upload-product.component.css'
})
export class UploadProductComponent {
  categories = ['All', 'Birthday', 'Love', 'Marriage', 'Grand-Opening', 'Sympathy', 'Get-well-soon'];

  product: ProductRequest = this.getEmptyProduct();
  successMessage = '';
  errorMessage = '';
  isSaving = false;

  constructor(private productService: ProductService) { }

  saveProduct(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (!this.hasAtLeastOnePrice()) {
      this.errorMessage = 'Enter at least one price: small, medium, or large.';
      return;
    }

    this.isSaving = true;
    this.productService.createProduct(this.product).subscribe({
      next: response => {
        this.successMessage = `Product "${response.name}" uploaded successfully with ID ${response.id}.`;
        this.product = this.getEmptyProduct();
        this.isSaving = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isSaving = false;
      }
    });
  }

  private hasAtLeastOnePrice(): boolean {
    return this.product.smallPrice !== null || this.product.mediumPrice !== null || this.product.largePrice !== null;
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
    return 'Unable to upload product. Please try again.';
  }
}
