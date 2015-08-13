class AddVideos < ActiveRecord::Migration
  def change
    create_table :videos do |t|
      t.string :title
      t.string :author
      t.string :url
      t.integer :week_number
      t.timestamps null: false
    end
  end
end
