 const form = document.getElementById('form-conversao');
    const resultado = document.getElementById('resultado');

    form.addEventListener('submit', async function(event) {
        event.preventDefault();

        const origem = document.getElementById('moeda-origem').value;
        const destino = document.getElementById('moeda-destino').value;

        const valorInput = document.getElementById('valor').value.trim().replace(',', '.');
        const valor = parseFloat(valorInput);

        if (isNaN(valor) || valor <= 0) {
            alert('Por favor, insira um valor válido!');
            return;
        }

        try {
            const response = await fetch(`https://v6.exchangerate-api.com/v6/b214842c6e4c47baf58c2196/latest/${origem}`);
            const data = await response.json();

            if (!data || data.result !== "success" || !data.conversion_rates || !data.conversion_rates[destino]) {
                resultado.innerHTML = 'Não foi possível obter a taxa de conversão. Verifique as moedas selecionadas.';
                return;
            }

            const taxaConversao = data.conversion_rates[destino];
            const valorConvertido = (valor * taxaConversao).toFixed(2);

            resultado.innerHTML = `${valor.toFixed(2)} ${origem} é igual a ${valorConvertido} ${destino}`;
        } catch (error) {
            resultado.innerHTML = 'Erro ao se comunicar com o servidor. Tente novamente!';
            console.error(error);
        }
    });