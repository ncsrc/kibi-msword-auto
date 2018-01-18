/************************************************************************
* CUSTOM BUTTONS extension for jTable                              *
*************************************************************************/
(function ($) {
    //Reference to base object members
    var base = {
        _addColumnsToHeaderRow: $.hik.jtable.prototype._addColumnsToHeaderRow,
        _addCellsToRowUsingRecord: $.hik.jtable.prototype._addCellsToRowUsingRecord
    };

    $.extend(true, $.hik.jtable.prototype, {
        options: {
            customButtons: {
                items: [] // fields: icon, text, click, position (left or right)
            }
        },

        /* Overrides base method to add custom buttons to header row.
         *************************************************************************/
        _addColumnsToHeaderRow: function ($tr) {
            base._addColumnsToHeaderRow.apply(this, arguments);
            for (var i = 0; i < this.options.customButtons.items.length; i++) {
                if (this.options.customButtons.items[i].position == 'left') {
                    $tr.prepend(this._createEmptyCommandHeader());
                } else {
                    $tr.append(this._createEmptyCommandHeader());
                }
            }
        },

        /* Overrides base method to add custom buttons to a row.
         *************************************************************************/
        _addCellsToRowUsingRecord: function ($row) {
            base._addCellsToRowUsingRecord.apply(this, arguments);

            var self = this;
            for (var i = 0; i < this.options.customButtons.items.length; i++) {
                var item = self.options.customButtons.items[i];
                $button = self._createCustomButton(item, $row.data('record'));
                var $buttonCell = $('<td></td>').addClass('jtable-command-column').append($button);
                if (item.position == 'left') {
                    $buttonCell.prependTo($row);
                } else {
                    $buttonCell.appendTo($row);
                }
            }
        },

        _createCustomButton: function (item, record) {
            var $button = $('<button title="' + item.text + '"></button>');
            $button.css('background', 'url(' + (item.icon ? item.icon : '../default-16x16.png') + ') no-repeat');
            $button.css('width', '16px');
            $button.css('height', '16px');
            $button.addClass('jtable-command-button');
            $button.append($('<span></span>').html(item.text));
            if (item.click) {
                $button.click(function () {
                    item.click(record);
                });
            }
            return $button;
        }
    });

})(jQuery);
