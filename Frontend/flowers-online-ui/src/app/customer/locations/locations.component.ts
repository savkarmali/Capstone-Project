import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ShopLocation, ShopLocationService } from '../../services/shop-location.service';

@Component({
  selector: 'app-locations',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './locations.component.html',
  styleUrl: './locations.component.css'
})
export class LocationsComponent implements OnInit {
  locations: ShopLocation[] = [];
  expandedLocationId: number | null = null;
  errorMessage = '';
  isLoading = false;

  constructor(private shopLocationService: ShopLocationService) { }

  ngOnInit(): void {
    this.loadLocations();
  }

  loadLocations(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.shopLocationService.getLocations().subscribe({
      next: locations => {
        this.locations = locations;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load shop locations.';
        this.isLoading = false;
      }
    });
  }

  toggleLocation(locationId: number): void {
    this.expandedLocationId = this.expandedLocationId === locationId ? null : locationId;
  }
}
