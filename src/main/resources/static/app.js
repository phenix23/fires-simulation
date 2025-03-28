document.addEventListener('DOMContentLoaded', function () {
    const gridElement = document.getElementById('grid');
    const nextStepButton = document.getElementById('nextStep');
    const resetButton = document.getElementById('reset');
    const stepCounter = document.getElementById('stepCounter');
    const statusElement = document.getElementById('status');

    let gridState = [];
    let step = 0;
    let active = true;

    function renderGrid() {
        gridElement.innerHTML = '';
        gridElement.style.gridTemplateColumns = `repeat(${gridState[0]?.length || 0}, 20px)`;

        gridState.forEach((row, i) => {
            row.forEach((cell, j) => {
                const cellElement = document.createElement('span');
                cellElement.className = 'cell';

                switch (cell) {
                    case 'TREE':
                        cellElement.classList.add('tree');
                        break;
                    case 'BURNING':
                        cellElement.classList.add('burning');
                        break;
                    case 'ASH':
                        cellElement.classList.add('ash');
                        break;
                }

                gridElement.appendChild(cellElement);
            });
        });

        stepCounter.textContent = `Étape: ${step}`;
        statusElement.textContent = `Statut: ${active ? 'Actif' : 'Terminé'}`;
    }

    function fetchSimulationState() {
        fetch('/api/simulation/current')
            .then(response => response.json())
            .then(data => {
                gridState = data.grid;
                step = data.step;
                active = data.active;
                renderGrid();
            });
    }

    function nextStep() {
        fetch('/api/simulation/next', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                gridState = data.grid;
                step = data.step;
                active = data.active;
                renderGrid();
            });
    }

    function resetSimulation() {
        fetch('/api/simulation/reset', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                gridState = data.grid;
                step = data.step;
                active = data.active;
                renderGrid();
            });
    }

    nextStepButton.addEventListener('click', nextStep);
    resetButton.addEventListener('click', resetSimulation);

    // Initial load
    fetchSimulationState();
});