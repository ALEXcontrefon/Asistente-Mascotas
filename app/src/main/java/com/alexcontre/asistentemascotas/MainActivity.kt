package com.alexcontre.asistentemascotas

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var petListContainer: LinearLayout
    private lateinit var reminderListContainer: LinearLayout
    private val petList = mutableListOf<Pet>()
    private val reminderList = mutableListOf<Reminder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        petListContainer = findViewById(R.id.petListContainer)
        reminderListContainer = findViewById(R.id.reminderListContainer)

        val petEditButton: Button = findViewById(R.id.editPetButton)
        petEditButton.setOnClickListener {
            val popupMenu = PopupMenu(this, petEditButton)
            menuInflater.inflate(R.menu.pet_settings_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.addPet -> {
                        showAddPetDialog()
                        true
                    }
                    R.id.editPet -> {
                        showEditPetDialog()
                        true
                    }
                    R.id.deletePet -> {
                        showDeletePetDialog()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        val backToLoginButton: Button = findViewById(R.id.backToLoginButton)
        backToLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la actividad actual (MainActivity)
        }

        val reminderEditButton: Button = findViewById(R.id.editReminderButton)
        reminderEditButton.setOnClickListener {
            val popupMenu = PopupMenu(this, reminderEditButton)
            menuInflater.inflate(R.menu.reminder_settings_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.addReminder -> {
                        showAddReminderDialog()
                        true
                    }
                    R.id.editReminder -> {
                        showEditReminderDialog()
                        true
                    }
                    R.id.deleteReminder -> {
                        showDeleteReminderDialog()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        updatePetList()
        updateReminderList()
    }

    private fun updatePetList() {
        petListContainer.removeAllViews()
        val titleTextView = TextView(this)
        titleTextView.text = "Pet List"
        titleTextView.textSize = 20f
        titleTextView.setTypeface(null, android.graphics.Typeface.BOLD)
        petListContainer.addView(titleTextView)

        for (pet in petList) {
            val textView = TextView(this)
            textView.text = "${pet.name}, ${pet.breed}, Age: ${pet.age}"
            textView.textSize = 18f
            petListContainer.addView(textView)
        }
    }

    private fun showAddPetDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_pet, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.petNameInput)
        val breedInput = dialogView.findViewById<EditText>(R.id.petBreedInput)
        val ageInput = dialogView.findViewById<EditText>(R.id.petAgeInput)

        AlertDialog.Builder(this)
            .setTitle("Add Pet")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameInput.text.toString().trim()
                val breed = breedInput.text.toString().trim()
                val age = ageInput.text.toString().trim().toIntOrNull() ?: 0

                if (name.isNotEmpty() && breed.isNotEmpty()) {
                    petList.add(Pet(name, breed, age))
                    updatePetList()
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditPetDialog() {
        if (petList.isEmpty()) {
            Toast.makeText(this, "No pets to edit", Toast.LENGTH_SHORT).show()
            return
        }

        val petNames = petList.map { it.name }.toTypedArray()
        var selectedPetIndex = 0

        AlertDialog.Builder(this)
            .setTitle("Select Pet to Edit")
            .setSingleChoiceItems(petNames, 0) { _, which ->
                selectedPetIndex = which
            }
            .setPositiveButton("Next") { _, _ ->
                val selectedPet = petList[selectedPetIndex]
                val dialogView = layoutInflater.inflate(R.layout.dialog_add_pet, null)
                val nameInput = dialogView.findViewById<EditText>(R.id.petNameInput)
                val breedInput = dialogView.findViewById<EditText>(R.id.petBreedInput)
                val ageInput = dialogView.findViewById<EditText>(R.id.petAgeInput)

                nameInput.setText(selectedPet.name)
                breedInput.setText(selectedPet.breed)
                ageInput.setText(selectedPet.age.toString())

                AlertDialog.Builder(this)
                    .setTitle("Edit Pet")
                    .setView(dialogView)
                    .setPositiveButton("Save") { _, _ ->
                        val name = nameInput.text.toString().trim()
                        val breed = breedInput.text.toString().trim()
                        val age = ageInput.text.toString().trim().toIntOrNull() ?: 0

                        if (name.isNotEmpty() && breed.isNotEmpty()) {
                            selectedPet.name = name
                            selectedPet.breed = breed
                            selectedPet.age = age
                            updatePetList()
                        } else {
                            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeletePetDialog() {
        if (petList.isEmpty()) {
            Toast.makeText(this, "No pets to delete", Toast.LENGTH_SHORT).show()
            return
        }

        val petNames = petList.map { it.name }.toTypedArray()
        var selectedPetIndex = 0

        AlertDialog.Builder(this)
            .setTitle("Select Pet to Delete")
            .setSingleChoiceItems(petNames, 0) { _, which ->
                selectedPetIndex = which
            }
            .setPositiveButton("Delete") { _, _ ->
                petList.removeAt(selectedPetIndex)
                updatePetList()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateReminderList() {
        reminderListContainer.removeAllViews()
        val titleTextView = TextView(this)
        titleTextView.text = "Reminder List"
        titleTextView.textSize = 20f
        titleTextView.setTypeface(null, android.graphics.Typeface.BOLD)
        reminderListContainer.addView(titleTextView)

        for (reminder in reminderList) {
            val textView = TextView(this)
            textView.text = "${reminder.title}: ${reminder.description}"
            textView.textSize = 18f
            reminderListContainer.addView(textView)
        }
    }

    private fun showAddReminderDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_reminder, null)
        val titleInput = dialogView.findViewById<EditText>(R.id.reminderTitleInput)
        val descriptionInput = dialogView.findViewById<EditText>(R.id.reminderDescriptionInput)

        AlertDialog.Builder(this)
            .setTitle("Add Reminder")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = titleInput.text.toString().trim()
                val description = descriptionInput.text.toString().trim()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    reminderList.add(Reminder(title, description))
                    updateReminderList()
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditReminderDialog() {
        if (reminderList.isEmpty()) {
            Toast.makeText(this, "No reminders to edit", Toast.LENGTH_SHORT).show()
            return
        }

        val reminderTitles = reminderList.map { it.title }.toTypedArray()
        var selectedReminderIndex = 0

        AlertDialog.Builder(this)
            .setTitle("Select Reminder to Edit")
            .setSingleChoiceItems(reminderTitles, 0) { _, which ->
                selectedReminderIndex = which
            }
            .setPositiveButton("Next") { _, _ ->
                val selectedReminder = reminderList[selectedReminderIndex]
                val dialogView = layoutInflater.inflate(R.layout.dialog_add_reminder, null)
                val titleInput = dialogView.findViewById<EditText>(R.id.reminderTitleInput)
                val descriptionInput = dialogView.findViewById<EditText>(R.id.reminderDescriptionInput)

                titleInput.setText(selectedReminder.title)
                descriptionInput.setText(selectedReminder.description)

                AlertDialog.Builder(this)
                    .setTitle("Edit Reminder")
                    .setView(dialogView)
                    .setPositiveButton("Save") { _, _ ->
                        val title = titleInput.text.toString().trim()
                        val description = descriptionInput.text.toString().trim()

                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            selectedReminder.title = title
                            selectedReminder.description = description
                            updateReminderList()
                        } else {
                            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteReminderDialog() {
        if (reminderList.isEmpty()) {
            Toast.makeText(this, "No reminders to delete", Toast.LENGTH_SHORT).show()
            return
        }

        val reminderTitles = reminderList.map { it.title }.toTypedArray()
        var selectedReminderIndex = 0

        AlertDialog.Builder(this)
            .setTitle("Select Reminder to Delete")
            .setSingleChoiceItems(reminderTitles, 0) { _, which ->
                selectedReminderIndex = which
            }
            .setPositiveButton("Delete") { _, _ ->
                reminderList.removeAt(selectedReminderIndex)
                updateReminderList()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    data class Pet(var name: String, var breed: String, var age: Int)
    data class Reminder(var title: String, var description: String)
}
