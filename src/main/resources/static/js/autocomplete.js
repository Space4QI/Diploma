document.addEventListener('DOMContentLoaded', function() {
    function setupAutocomplete(inputId, hiddenId, listClass) {
        const searchInput = document.getElementById(inputId);
        const hiddenUserId = document.getElementById(hiddenId);
        const resultList = searchInput.parentElement.querySelector(`.${listClass}`);

        if (searchInput) {
            searchInput.addEventListener('input', function() {
                const query = this.value.trim();
                if (query.length === 0) {
                    resultList.innerHTML = '';
                    return;
                }
                fetch(`/admin/search-users?query=${encodeURIComponent(query)}`)
                    .then(response => response.json())
                    .then(users => {
                        resultList.innerHTML = '';
                        users.forEach(user => {
                            const item = document.createElement('a');
                            item.href = '#';
                            item.className = 'list-group-item list-group-item-action';
                            item.textContent = user.nickname;
                            item.dataset.userId = user.id;
                            item.addEventListener('click', function(e) {
                                e.preventDefault();
                                searchInput.value = this.textContent;
                                hiddenUserId.value = this.dataset.userId;
                                resultList.innerHTML = '';
                            });
                            resultList.appendChild(item);
                        });
                    })
                    .catch(error => {
                        console.error('Ошибка при загрузке пользователей:', error);
                    });
            });

            document.addEventListener('click', function(e) {
                if (!resultList.contains(e.target) && e.target !== searchInput) {
                    resultList.innerHTML = '';
                }
            });
        }
    }

    setupAutocomplete('user-search', 'selectedUserId', 'autocomplete-list');
    setupAutocomplete('role-user-search', 'role-selectedUserId', 'autocomplete-list');
});
