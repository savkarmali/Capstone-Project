import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ShopLocation, ShopLocationRequest, ShopLocationService } from '../../services/shop-location.service';

@Component({
  selector: 'app-manage-locations',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-locations.component.html',
  styleUrl: './manage-locations.component.css'
})
export class ManageLocationsComponent implements OnInit {
  locations: ShopLocation[] = [];
  locationForm: ShopLocationRequest = this.getEmptyForm();
  editingLocationId: number | null = null;
  successMessage = '';
  errorMessage = '';
  isSaving = false;
  isLoading = false;

  constructor(private shopLocationService: ShopLocationService) { }

  ngOnInit(): void {
    this.loadLocations();
  }

  loadLocations(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.shopLocationService.getAdminLocations().subscribe({
      next: (response: ShopLocation[]) => {
        this.locations = response;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load shop locations.';
        this.isLoading = false;
      }
    });
  }

  saveLocation(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (!this.isFormValid()) {
      this.errorMessage = 'Please fill all fields. Phone number must contain 10 digits.';
      return;
    }

    this.isSaving = true;

    if (this.editingLocationId === null) {
      this.shopLocationService.createLocation(this.locationForm).subscribe({
        next: () => this.handleSaveSuccess('Shop location added successfully.'),
        error: () => this.handleSaveError()
      });
    } else {
      this.shopLocationService.updateLocation(this.editingLocationId, this.locationForm).subscribe({
        next: () => this.handleSaveSuccess('Shop location updated successfully.'),
        error: () => this.handleSaveError()
      });
    }
  }

  editLocation(location: ShopLocation): void {
    this.editingLocationId = location.id;
    this.locationForm = {
      shopName: location.shopName,
      address: location.address,
      city: location.city,
      country: location.country,
      phoneNumber: location.phoneNumber
    };
    this.successMessage = '';
    this.errorMessage = '';
  }

  deleteLocation(locationId: number): void {
    const confirmed = window.confirm('Do you want to delete this shop location?');

    if (!confirmed) {
      return;
    }

    this.shopLocationService.deleteLocation(locationId).subscribe({
      next: () => {
        this.successMessage = 'Shop location deleted successfully.';
        this.loadLocations();
      },
      error: () => {
        this.errorMessage = 'Unable to delete shop location.';
      }
    });
  }

  cancelEdit(): void {
    this.editingLocationId = null;
    this.locationForm = this.getEmptyForm();
    this.successMessage = '';
    this.errorMessage = '';
  }

  private handleSaveSuccess(message: string): void {
    this.successMessage = message;
    this.isSaving = false;
    this.cancelEdit();
    this.successMessage = message;
    this.loadLocations();
  }

  private handleSaveError(): void {
    this.errorMessage = 'Unable to save shop location.';
    this.isSaving = false;
  }

  private isFormValid(): boolean {
    return this.locationForm.shopName.trim().length > 0
      && this.locationForm.address.trim().length > 0
      && this.locationForm.city.trim().length > 0
      && this.locationForm.country.trim().length > 0
      && /^[0-9]{10}$/.test(this.locationForm.phoneNumber);
  }

  private getEmptyForm(): ShopLocationRequest {
    return {
      shopName: '',
      address: '',
      city: '',
      country: '',
      phoneNumber: ''
    };
  }
}
