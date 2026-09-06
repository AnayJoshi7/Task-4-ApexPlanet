package com.anay.fitnesstracker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anay.fitnesstracker.data.model.Profile
import com.anay.fitnesstracker.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                _profile.value = repository.getProfile()

            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load profile"
                android.util.Log.e("ProfileViewModel", "Failed to load profile", e)
            }finally {
                _isLoading.value = false
            }
        }
    }
}