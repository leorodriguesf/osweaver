return {
    "nvim-telescope/telescope.nvim",
    dependencies = {'nvim-lua/plenary.nvim'},
    config = function()
        local builtin = require("telescope.builtin")
        vim.keymap.set("n", "<leader>ff", builtin.find_files)
        vim.keymap.set("n", "<leader>fr", builtin.oldfiles)
        vim.keymap.set("n", "<leader>sg", builtin.live_grep)
        vim.keymap.set("n", "<leader>sb", builtin.buffers)
    end
}

