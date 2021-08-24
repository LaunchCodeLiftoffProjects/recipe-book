const addButton = document.getElementById("add");
//const deleteButton = document.getElementById("delete");
const input = document.getElementById("ingredientInput");
const label = document.getElementById("ingredientsLabel");
const hiddenIngredients = document.getElementById("hiddenIngredients");
const ul =document.createElement("ul");

//This function allows users to add ingredients to the recipe
addButton.addEventListener("click", function() {
    // create list-item and add attributes
    const li = document.createElement("li");
    ul.classList.add("list-group");
    ul.id = "ingredients-list";
    li.classList.add("list-group-item");
    li.setAttribute("name",input.value.toLowerCase());
    // add list to document
    li.appendChild(document.createTextNode(input.value));
    ul.appendChild(li);
    label.appendChild(ul);
    // assign ingredients to recipe and clear input field
    hiddenIngredients.value += input.value + ", ";
    input.value = "";
})

////This function allows admins to delete categories
//deleteButton.addEventListener("click", function() {
//
//})

