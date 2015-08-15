class AddColumnsToVideos < ActiveRecord::Migration
  def change
    add_column :videos, :is_beginner, :boolean, default: false
    add_column :videos, :is_advanced, :boolean, default: false
    add_column :videos, :semester_number, :integer
  end
end
